package com.example.cirrusmobileapp.presentation.navigation.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cirrusmobileapp.presentation.navigation.Destinations

@Composable
fun MoreMenuItem(
    navController: NavController,
    isMenuExpanded: Boolean,
    onMenuExpandedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .clickable(onClick = { onMenuExpandedChange(!isMenuExpanded) })
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.MoreHoriz,
            contentDescription = "More options",
            tint = Color.Black,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = "More",
            fontSize = 12.sp,
            fontWeight = FontWeight.W300,
            modifier = Modifier.padding(start = 8.dp),
            color = Color.Black
        )
    }

    DropdownMenu(
        modifier = Modifier
            .width(220.dp)
            .background(Color.White),
        offset = DpOffset(x = 60.dp, y = 0.dp),
        expanded = isMenuExpanded,
        onDismissRequest = { onMenuExpandedChange(false) }
    ) {
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Default.ViewInAr, contentDescription = "Catalog", tint = Color.Black, modifier = Modifier.size(24.dp)) },
            text = { Text("Catalog", color = Color.Black, fontWeight = FontWeight.W400, modifier = Modifier.padding(start = 6.dp)) },
            onClick = {
                navController.navigate(Destinations.Catalog.route)
                onMenuExpandedChange(false)
            }
        )
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Default.CurrencyExchange, contentDescription = "Order History", tint = Color.Black, modifier = Modifier.size(24.dp)) },
            text = { Text("Order History", color = Color.Black, fontWeight = FontWeight.W400, modifier = Modifier.padding(start = 6.dp)) },
            onClick = { onMenuExpandedChange(false) }
        )
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Default.Compare, contentDescription = "Competitor Products", tint = Color.Black, modifier = Modifier.size(24.dp)) },
            text = { Text("Competitor Products", color = Color.Black, fontWeight = FontWeight.W400, modifier = Modifier.padding(start = 6.dp)) },
            onClick = { onMenuExpandedChange(false) }
        )
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Default.LocalOffer, contentDescription = "Promotions", tint = Color.Black, modifier = Modifier.size(24.dp)) },
            text = { Text("Promotions", color = Color.Black, fontWeight = FontWeight.W400, modifier = Modifier.padding(start = 6.dp)) },
            onClick = { onMenuExpandedChange(false) }
        )
    }
}