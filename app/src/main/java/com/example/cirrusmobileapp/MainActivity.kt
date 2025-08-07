package com.example.cirrusmobileapp

import android.os.Bundle
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cirrusmobileapp.presentation.viewmodel.AppViewModel
import com.example.cirrusmobileapp.presentation.navigation.bottom_navigation.BottomNavigationBar
import com.example.cirrusmobileapp.presentation.navigation.Destinations
import com.example.cirrusmobileapp.presentation.navigation.top_bar.AppTopBar
import com.example.cirrusmobileapp.presentation.screens.calendar.CalendarScreen
import com.example.cirrusmobileapp.presentation.screens.catalog.CatalogScreen
import com.example.cirrusmobileapp.presentation.screens.customer.CustomerScreen
import com.example.cirrusmobileapp.presentation.screens.home.HomeScreen
import com.example.cirrusmobileapp.presentation.screens.signIn.LoginScreen
import com.example.cirrusmobileapp.presentation.screens.sync.SyncScreen
import com.example.cirrusmobileapp.presentation.ui.AppContainer
import com.example.cirrusmobileapp.ui.theme.CirrusMobileAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CirrusMobileAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showScaffold = currentRoute != Destinations.LoginScreen.route

    AppContainer {
        Scaffold(
            topBar = {
                if (showScaffold) {
                    val appViewModel: AppViewModel = hiltViewModel()
                    AppTopBar(appViewModel)
                }
            },
            bottomBar = {
                if (showScaffold) {
                    BottomNavigationBar(
                        navController = navController,
                        currentDestination = navBackStackEntry?.destination
                    )
                }
            },
            floatingActionButton = {
                if (showScaffold) {
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
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destinations.LoginScreen.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Destinations.LoginScreen.route) {
                    LoginScreen(navController)
                }
                composable(Destinations.SyncScreen.route) {
                    SyncScreen(navController)
                }
                composable(Destinations.HomeScreen.route) {
                    HomeScreen(navController)
                }
                composable(Destinations.Customers.route) {
                    CustomerScreen(navController)
                }
                composable(Destinations.Catalog.route) {
                    val appViewModel: AppViewModel = hiltViewModel()
                    CatalogScreen(navController, appViewModel)
                }
                composable(Destinations.Calendar.route) {
                    CalendarScreen(navController)
                }
            }
        }
    }
}