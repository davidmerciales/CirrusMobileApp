package com.example.cirrusmobileapp.presentation.common.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.cirrusmobileapp.presentation.viewmodel.AppNotification
import kotlin.math.roundToInt

@Composable
fun NotificationPopUpList(
    notifications: List<AppNotification>,
    onMarkAllAsRead: () -> Unit,
    onNotificationClick: (AppNotification) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val unreadCount = notifications.count { !it.isRead }
    val density = LocalDensity.current
    var iconBounds by remember { mutableStateOf<Rect?>(null) }  

    Box {
        Box(
            modifier = Modifier
                .size(35.dp)
                .onGloballyPositioned { coordinates ->
                    val position = coordinates.positionInWindow()
                    val size = with(density) {
                        coordinates.size.toSize()
                    }
                    iconBounds = Rect(position.x, position.y, position.x + size.width, position.y + size.height)
                }
                .wrapContentSize(Alignment.TopEnd)
                .clickable { expanded = !expanded },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.NotificationsNone,
                contentDescription = "Notifications",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )

            if (unreadCount > 0) {
                Box(
                    modifier = Modifier
                        .offset(x = 1.dp)
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(color = Color.Red)
                        .align(Alignment.TopEnd)
                        .padding(start = 1.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val displayText = if (unreadCount > 99) "99+" else unreadCount.toString()
                    Text(
                        text = displayText,
                        color = Color.White,
                        fontSize = 7.sp,
                        fontWeight = FontWeight.W700,
                        lineHeight = 8.sp
                    )
                }
            }
        }

        if (expanded) {
            iconBounds?.let { bounds ->
                NotificationPopupMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    notifications = notifications,
                    onNotificationClick = {
                        onNotificationClick(it)
                        expanded = false
                    },
                    onMarkAllAsRead = {
                        onMarkAllAsRead()
                        expanded = false
                    },
                    anchorBounds = bounds,
                    density = density
                )
            }
        }
    }
}

@Composable
fun NotificationPopupMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    notifications: List<AppNotification>,
    onNotificationClick: (AppNotification) -> Unit,
    onMarkAllAsRead: () -> Unit,
    anchorBounds: Rect,
    density: androidx.compose.ui.unit.Density
) {
    if (!expanded) return
    val sortedNotifications = remember(notifications) {
        notifications.sortedWith(
            compareBy<AppNotification> { it.isRead }.thenByDescending { it.timestamp }
        )
    }
    Popup(
        alignment = Alignment.TopStart,
        offset = with(density) {
            IntOffset(
                x = (anchorBounds.right - 300.dp.toPx()).roundToInt(),
                y = (anchorBounds.bottom - 6.dp.toPx()).roundToInt()
            )
        },
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true)
    ) {
        Surface(
            tonalElevation = 4.dp,
            shape = MaterialTheme.shapes.medium,
            color = Color.White,
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = .3f)),
            modifier = Modifier
                .width(300.dp)
                .heightIn(max = 410.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Notifications", style = MaterialTheme.typography.titleMedium)
                    TextButton(onClick = onMarkAllAsRead) {
                        Text("Mark all as read")
                    }
                }
                HorizontalDivider()
                if (sortedNotifications.isEmpty()) {
                    Text(
                        text = "No new notifications.",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 320.dp)
                    ) {
                        items(sortedNotifications) { notification ->
                            NotificationItem(
                                notification = notification,
                                onClick = { onNotificationClick(notification) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(
    notification: AppNotification,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .background(if (notification.isRead) Color.White else Color.LightGray.copy(alpha = 0.2f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(5.dp))
        if (!notification.isRead) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(modifier = Modifier.weight(.9f)) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = notification.title,
                color = Color.DarkGray.copy(alpha = .8f),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (notification.isRead) null else FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}