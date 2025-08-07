package com.example.cirrusmobileapp.presentation.screens.sync

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.example.cirrusmobileapp.R
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.cirrusmobileapp.presentation.navigation.Destinations
import com.example.cirrusmobileapp.presentation.viewmodel.sync.SyncContract
import com.example.cirrusmobileapp.presentation.viewmodel.sync.SyncViewModel

@Composable
fun SyncScreen(navController: NavController) {
    val viewModel: SyncViewModel = hiltViewModel()

    val state by viewModel.uiState.collectAsState()
    val effectFlow = viewModel.effect

    val context = LocalContext.current
    var isPlaying by remember {
        mutableStateOf(true)
    }

// for speed
    var speed by remember {
        mutableStateOf(1.3f)
    }

    LaunchedEffect(Unit) {
        viewModel.processIntent(SyncContract.Intent.StartSync)
    }

    val compositionSyncing by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.lottie_syncing)
    )

    val compositionSuccess by rememberLottieComposition(
        LottieCompositionSpec
            .RawRes(R.raw.lottie_success)
    )

    val progressSyncing by animateLottieCompositionAsState(
        compositionSyncing,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    val progressSuccess by animateLottieCompositionAsState(
        compositionSuccess,
        iterations = 3,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false
    )

    // Collect one-time effects
    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is SyncContract.Effect.ShowSuccess -> {
                }

                is SyncContract.Effect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSyncing -> {
                Box(modifier = Modifier
                    .background(Color.White)
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        Modifier.background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        LottieAnimation(
                            modifier = Modifier
                                .size(60.dp),
                            composition = compositionSyncing,
                            progress = progressSyncing,
                        )
//                        Text(state.productName,
//                            fontSize = 12.sp,
//                            color = Color.Black,
//                            lineHeight = 1.2.em
//                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(state.syncingMessage,
                            color = Color.Black
                        )
                    }
                }
            }

            state.error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Error, contentDescription = "Error", tint = Color.Red)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Failed to sync data.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(state.error ?: "", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        //viewModel.dispatch(SyncContract.Intent.RetrySync)
                    }) {
                        Text("Retry")
                    }
                }
            }

            else -> {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.LightGray.copy(.4f))
                        .clickable{
                            navController.navigate(Destinations.HomeScreen.route) {
                                popUpTo(Destinations.SyncScreen.route) { inclusive = true }
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    AnimatedVisibility(
                        visible = true,
                        enter = scaleIn(
                            initialScale = 0.1f,
                            animationSpec = tween(durationMillis = 2000)
                        ) + expandIn(
                            animationSpec = tween(durationMillis = 2000)
                        ),
                    ) {
                        LottieAnimation(
                            modifier = Modifier
                                .size(200.dp),
                            composition = compositionSuccess,
                            progress = progressSuccess,
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Sync successful!",
                        color = Color.Black)
                    Spacer(modifier = Modifier.height(80.dp))
                    Text("Please tap to continue.",
                        fontSize = 12.sp,
                        fontWeight = W300,
                        color = Color.Black)
                }
            }
        }
    }
}