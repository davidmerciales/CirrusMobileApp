package com.example.cirrusmobileapp.presentation.navigation.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.cirrusmobileapp.presentation.navigation.Destinations


@Composable
fun BottomNavigationItem(
    navController: NavController,
    destination: Destinations,
    selected: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(.65f)
            .background(
                color = if (selected) Color.Black else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 5.dp)
            .clickable(
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconColor = if (selected) Color.White else Color.Black
        val textColor = if (selected) Color.White else Color.Black

        when (destination) {
            is Destinations.HomeScreen -> Icon(Icons.Outlined.Home, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            is Destinations.Customers -> Icon(Icons.Outlined.Person, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            is Destinations.Catalog -> Icon(Icons.Outlined.Build, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            is Destinations.Calendar -> Icon(Icons.Outlined.DateRange, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            else -> {}
        }

        Text(
            text = destination::class.simpleName ?: "",
            fontSize = 12.sp,
            fontWeight = FontWeight.W300,
            modifier = Modifier.padding(start = 8.dp),
            color = textColor
        )
    }
}