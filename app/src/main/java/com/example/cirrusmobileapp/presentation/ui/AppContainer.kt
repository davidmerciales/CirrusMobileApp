package com.example.cirrusmobileapp.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cirrusmobileapp.presentation.AppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Main container that wraps the entire application UI and provides a global notification system.
 *
 * This composable observes the AppViewModel's notification events and displays
 * a custom notification with a sliding animation at the top right of the screen.
 * It takes a `content` lambda, which would typically be your `Scaffold` and `NavHost`,
 * allowing any screen to trigger a notification while the global UI remains active.
 *
 * @param appViewModel The ViewModel to observe for notification events.
 * @param content The main content of your application, such as a Scaffold with a NavHost.
 */
@Composable
fun AppContainer(appViewModel: AppViewModel = viewModel(), content: @Composable () -> Unit) {
    var showNotification by remember { mutableStateOf(false) }
    var notificationTitle by remember { mutableStateOf("") }
    var notificationMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        appViewModel.notificationEvents.collect { event ->
            notificationMessage = event.description
            notificationTitle = event.title
            showNotification = true
            coroutineScope.launch {
                delay(3000L)
                showNotification = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        content()

        AnimatedVisibility(
            visible = showNotification,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 80.dp),
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 800)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        ) {
            Box(
                modifier = Modifier
                    .height(78.dp)
                    .width(340.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray.copy(alpha = .3f), RoundedCornerShape(8.dp))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(8.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                        ) {
                            Text(
                                text = notificationTitle,
                                color = Color.Black.copy(alpha = .55f),
                                fontWeight = FontWeight.W500,
                                fontSize = 14.sp
                            )
                            Text(
                                text = notificationMessage,
                                color = Color.Black.copy(alpha = .5f),
                                fontWeight = FontWeight.W400,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 1.2.em
                            )
                        }
                    }
                }

            }
        }
    }
}
