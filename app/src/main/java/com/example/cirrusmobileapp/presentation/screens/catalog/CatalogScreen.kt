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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cirrusmobileapp.presentation.AppViewModel
import com.example.cirrusmobileapp.presentation.screens.catalog.sections.CustomerDetailsSection
import com.example.cirrusmobileapp.presentation.screens.catalog.sections.OrderDetailsSection
import com.example.cirrusmobileapp.presentation.screens.catalog.sections.ProductListSection

@Composable
fun CatalogScreen(
    navController: NavController,
    appViewModel: AppViewModel
) {

    val products = listOf(
        Product(
            name = "APO 50 EC",
            type = "Insecticide",
            description = "Your thrips specialist, APO 50EC: Outstanding ang Proteksyon at Aksyon laban sa Thrips",
            pricePerPc = 700.0,
            sizeVolumes = "1000 ml",
            variations = listOf("1000ml", "100g")
        ),
        Product(
            name = "Item 2",
            type = "Type B",
            description = "Description for Item 2",
            pricePerPc = 200.0,
            sizeVolumes = "1 L",
            variations = listOf("1000ml", "100g")
        ),
        Product(
            name = "Item 3",
            type = "Type A",
            description = "Description for Item 3",
            pricePerPc = 150.0,
            sizeVolumes = "250 ml",
            variations = listOf("1000ml", "100g")
        ),
        Product(
            name = "Item 4",
            type = "Type C",
            description = "Description for Item 4",
            pricePerPc = 300.0,
            sizeVolumes = "2 L",
            variations = listOf("1000ml", "100g")
        ),
        Product(
            name = "Item 5",
            type = "Type B",
            description = "Description for Item 5",
            pricePerPc = 250.0,
            sizeVolumes = "750 ml",
            variations = listOf("1000ml", "100g")
        ),
    )

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
                products
            )
            OrderDetailsSection(
                modifier = Modifier
                    .weight(.29f)
                    .fillMaxHeight()
                    .padding(top = 28.dp, start = 18.dp, end = 22.dp, bottom = 10.dp)
                    .border(1.dp, Color.LightGray.copy(alpha = .3f), RoundedCornerShape(8.dp)),
                onOrderClick = {
                    appViewModel.showNotification("Order","Order placed successfully!")
                }
            )
        }
    }
}

data class Product(
    val name: String,
    val type: String,
    val conversion: String? = "1 carton = 40 pieces",
    val variations: List<String>,
    val description: String,
    val pricePerPc: Double,
    val sizeVolumes: String
)