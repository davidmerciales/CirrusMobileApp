package com.example.cirrusmobileapp.presentation.screens.sync

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isSyncing -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Syncing data, please wait…")
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Success", tint = Color.Green)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Data synced successfully!")
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        navController.navigate(Destinations.HomeScreen.route) {
                            popUpTo(Destinations.SyncScreen.route) { inclusive = true }
                        }
                    }) {
                        Text("Continue")
                    }
                }
            }
        }
    }
}