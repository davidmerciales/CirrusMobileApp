package com.example.cirrusmobileapp.presentation.screens.sync

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cirrusmobileapp.presentation.navigation.Destinations
import com.example.cirrusmobileapp.presentation.viewmodel.sync.SyncContract
import com.example.cirrusmobileapp.presentation.viewmodel.sync.SyncViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun SyncScreen(navController: NavController) {
    val viewModel: SyncViewModel = hiltViewModel()

    val state by viewModel.uiState.collectAsState()
    val effectFlow = viewModel.effect

    val progressState by viewModel.syncProgress.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.processIntent(SyncContract.Intent.StartSync)
    }

    // Collect one-time effects
    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            when (effect) {
                is SyncContract.Effect.ShowSuccess -> {
                    navController.navigate(Destinations.HomeScreen.route) {
                        popUpTo(Destinations.SyncScreen.route) { inclusive = true }
                    }
                }

                is SyncContract.Effect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // UI rendering
    Box(Modifier
        .fillMaxSize()
        .background(Color(0xFF7e7e7e)),
        contentAlignment = Alignment.Center
    ){
        Box(Modifier
            .fillMaxHeight(.84f)
            .fillMaxWidth(.6f)
            .border(.5.dp, Color.DarkGray.copy(.8f), RoundedCornerShape(20.dp))
            .background(Color.White, RoundedCornerShape(20.dp))
        ){
            Column(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
            ) {
                Text(
                    text = "Loading Data",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    lineHeight = 1.4.em,
                    color = Color.Black.copy(.8f)
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "We're setting things up for you. This wont take long!",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    lineHeight = 1.4.em,
                    color = Color.Black.copy(.8f)
                )

                Spacer(Modifier.height(22.dp))

                HorizontalDivider(
                    thickness = .8.dp,
                    color = Color.LightGray.copy(.6f)
                )

                Spacer(Modifier.height(22.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(.6f)
                        .padding(horizontal = 6.dp)
                ) {
                    items(1){
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(18.dp),
                                    color = Color.Black,
                                    trackColor = Color(0xFF858585),
                                    strokeWidth = 2.dp
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = "Products",
                                    modifier = Modifier
                                        .weight(1f),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400,
                                    lineHeight = 1.4.em,
                                    color = Color.Black.copy(.8f)
                                )
                                Text(
                                    text = "${progressState.progress}% complete",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W400,
                                    lineHeight = 1.4.em,
                                    color = Color.Black.copy(.8f)
                                )
                            }

                            Spacer(Modifier.height(22.dp))

                            LinearProgressIndicator(
                                progress ={ progressState.progress / 100f },
                                modifier = Modifier
                                    .height(14.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(20.dp))
                                ,
                                color = Color.Black,
                                trackColor = Color.LightGray,
                                strokeCap = StrokeCap.Round
                            )
                        }
                    }
                }

                HorizontalDivider(
                    thickness = .8.dp,
                    color = Color.LightGray.copy(.6f)
                )
                Column(
                    modifier = Modifier
                        .weight(.4f)
                ) {

                    Spacer(Modifier.height(22.dp))

                    Text(
                        text = "Information",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700,
                        lineHeight = 1.4.em,
                        color = Color.Black.copy(.8f)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "We are preparing your dashboard by loading data and updating information. This will only take a few moments.",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        lineHeight = 1.4.em,
                        color = Color.Black.copy(.8f)
                    )

                    Spacer(Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(.74f)
                                .fillMaxWidth(.28f)
                                .border(2.4.dp, Color.Black, RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Exit Application",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                lineHeight = 1.4.em,
                                color = Color.Black.copy(.8f)
                            )
                        }
                        Spacer(Modifier.width(14.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxHeight(.74f)
                                .fillMaxWidth(.4f)
                                .background(Color.Black, RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Contact Support",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W600,
                                lineHeight = 1.4.em,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

    }
}