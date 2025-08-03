package com.example.cirrusmobileapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cirrusmobileapp.data.websocket.WebSocketServiceImpl
import com.example.cirrusmobileapp.domain.websocket.WebSocketEvent
import com.example.cirrusmobileapp.domain.websocket.WebSocketService
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cirrusmobileapp.presentation.navigation.bottom_navigation.BottomNavigationBar
import com.example.cirrusmobileapp.presentation.navigation.Destinations
import com.example.cirrusmobileapp.presentation.navigation.top_bar.AppTopBar
import com.example.cirrusmobileapp.presentation.screens.calendar.CalendarScreen
import com.example.cirrusmobileapp.presentation.screens.catalog.CatalogScreen
import com.example.cirrusmobileapp.presentation.screens.customer.CustomerScreen
import com.example.cirrusmobileapp.presentation.screens.home.HomeScreen
import com.example.cirrusmobileapp.ui.theme.CirrusMobileAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var webSocketService: WebSocketService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CirrusMobileAppTheme {
                MainScreen()
            }
        }

        // Initialize and connect
        webSocketService = WebSocketServiceImpl("ws://10.0.2.2:8080/ws")
        webSocketService.connect { event ->
            when (event) {
                is WebSocketEvent.OnOpen -> Log.d("WebSocket", "Connected!")
                is WebSocketEvent.OnMessage -> Log.d("WebSocket", "Received: ${event.message}")
                is WebSocketEvent.OnFailure -> Log.e("WebSocket", "Error: ${event.throwable}")
                is WebSocketEvent.OnClosed -> Log.d("WebSocket", "Closed")
            }
        }

        // Delay then send a test message
        Handler(Looper.getMainLooper()).postDelayed({
            webSocketService.send("Hello from MainActivity")
        }, 2000)
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    Scaffold(
        topBar = {
            AppTopBar()
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier
                    .size(70.dp)
                    .offset(y = (48).dp),
                containerColor = Color.Black,
                shape = RoundedCornerShape(50),
                onClick = {
                    navController.navigate(Destinations.Catalog.route)
                }
            ) {
                Icon(
                    modifier = Modifier.size(35.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Destinations.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Destinations.HomeScreen.route) {
                HomeScreen(navController)
            }
            composable(Destinations.Customers.route) {
                CustomerScreen(navController)
            }
            composable(Destinations.Catalog.route) {
                CatalogScreen(navController)
            }
            composable(Destinations.Calendar.route) {
                CalendarScreen(navController)
            }
        }
    }
}