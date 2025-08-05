package com.example.cirrusmobileapp.presentation.navigation.top_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cirrusmobileapp.R
import com.example.cirrusmobileapp.presentation.common.notification.NotificationPopUpList
import com.example.cirrusmobileapp.presentation.viewmodel.AppNotification
import com.example.cirrusmobileapp.presentation.viewmodel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    appViewModel: AppViewModel
) {
    val onNotificationClick: (AppNotification) -> Unit = { clickedNotif ->
        appViewModel.markNotificationAsRead(clickedNotif.id)
    }

    val onMarkAllAsRead: () -> Unit = {
        appViewModel.markAllAsRead()
    }

    Surface(
        shadowElevation = 4.dp
    ) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .offset(y = -(8).dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jdi_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(52.dp)
                )
                Text(
                    text = "Jardine Distribution, Inc.",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .offset(y = -(8).dp),
            ) {
                val notifications by appViewModel.notifications.collectAsState()
                NotificationPopUpList(notifications, onMarkAllAsRead, onNotificationClick)

                ProfileDropdown()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,

        )
    )
}
    }