package com.example.cirrusmobileapp.presentation.screens.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cirrusmobileapp.presentation.viewmodel.AppViewModel
import com.example.cirrusmobileapp.presentation.screens.catalog.sections.CustomerDetailsSection
import com.example.cirrusmobileapp.presentation.screens.catalog.sections.OrderDetailsSection
import com.example.cirrusmobileapp.presentation.screens.catalog.sections.ProductListSection
import com.example.cirrusmobileapp.presentation.viewmodel.catalog.CatalogContract
import com.example.cirrusmobileapp.presentation.viewmodel.catalog.CatalogViewModel

@Composable
fun CatalogScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row {
            CustomerDetailsSection(
                modifier = Modifier
                    .weight(.29f)
                    .fillMaxHeight()
                    .padding(26.dp)
            )
            ProductListSection(
                modifier = Modifier
                    .padding(top = 26.dp)
                    .weight(.42f)
                    .fillMaxHeight(),
            )
            OrderDetailsSection(
                modifier = Modifier
                    .weight(.29f)
                    .fillMaxHeight()
                    .padding(top = 28.dp, start = 18.dp, end = 22.dp, bottom = 10.dp)
                    .border(1.dp, Color.LightGray.copy(alpha = .3f), RoundedCornerShape(8.dp)),
                onOrderClick = {
                    appViewModel.showNotification("","Order","Order placed successfully!")
                }
            )
        }
    }
}