package com.example.cirrusmobileapp.presentation.screens.catalog.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cirrusmobileapp.presentation.common.dropdown.CommonDropDown

@Composable
fun CustomerDetailsSection(
    modifier: Modifier
) {
    Column (
        modifier = modifier
    ){
        Text("Customer Details",
            color = Color.Black,
            fontWeight = FontWeight.W600,
            fontSize = 16.sp)

        CommonDropDown(
            modifier = Modifier
                .padding(top = 16.dp),
            "Customer Type",
            listOf("Distributor", "Dealer", "Dealer Distributor "),
            onItemSelected = {}
        )

        CommonDropDown(
            modifier = Modifier
                .padding(top = 16.dp),
            "Customer",
            listOf("Del Rosario Agro Supplies", "Customer 2", "Customer 3"),
            onItemSelected = {}
        )

        CommonDropDown(
            modifier = Modifier
                .padding(top = 16.dp),
            "Activity Type",
            listOf("Distributor Call", "Miscellaneous", "JDI Events"),
            onItemSelected = {}
        )
    }
}