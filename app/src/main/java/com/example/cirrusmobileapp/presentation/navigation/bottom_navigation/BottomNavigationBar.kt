package com.example.cirrusmobileapp.presentation.navigation.bottom_navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.cirrusmobileapp.presentation.navigation.Destinations

@Composable
fun BottomNavigationBar(navController: NavController, currentDestination: NavDestination?) {
    val items = listOf(
        Destinations.HomeScreen,
        Destinations.Customers,
        Destinations.Calendar,
        Destinations.More
    )

    var isMoreMenuExpanded by remember { mutableStateOf(false) }

    Surface(
        shadowElevation = 8.dp,
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { destination ->
                val selected = currentDestination?.route?.equals(destination.route, true) == true

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (destination is Destinations.More) {
                        MoreMenuItem(
                            navController = navController,
                            isMenuExpanded = isMoreMenuExpanded,
                            onMenuExpandedChange = { isMoreMenuExpanded = it }
                        )
                    } else {
                        BottomNavigationItem(
                            navController = navController,
                            destination = destination,
                            selected = selected
                        )
                    }
                }
            }
        }
    }
}


