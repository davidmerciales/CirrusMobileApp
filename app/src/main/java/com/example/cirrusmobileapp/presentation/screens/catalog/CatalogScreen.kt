package com.example.cirrusmobileapp.presentation.screens.catalog

import android.widget.GridView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cirrusmobileapp.R
import com.example.cirrusmobileapp.presentation.common.dropdown.CommonDropDown
import com.example.cirrusmobileapp.presentation.common.search_bar.CommonSearchBar

@Composable
fun CatalogScreen(
    navController: NavController
){

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
    ){
        Row{
            Column (
                modifier = Modifier
                    .weight(.29f)
                    .fillMaxHeight()
                    .padding(26.dp)
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
            Column (
                modifier = Modifier
                    .padding(top = 26.dp)
                    .weight(.42f)
                    .fillMaxHeight(),
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CommonSearchBar(
                        modifier = Modifier
                            .weight(.85f)
                            .height(40.dp)
                        ,
                        placeholder = "Search product",
                        onSearch = {}
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    Row(
                        modifier = Modifier
                            .weight(.15f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = Color.Black,
                            modifier = Modifier
                                .height(56.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Filter",
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(products){ product->
                        var selectedVariation by remember { mutableStateOf(product.variations.firstOrNull()) }

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .height(390.dp)
                                .weight(.5f)
                                .background(
                                    color = Color(0xFFf8f8f8),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .border(
                                    width = .6.dp,
                                    color = Color.LightGray.copy(alpha = .4f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ){
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.sample_product_1),
                                    contentDescription = "App Logo",
                                    modifier = Modifier
                                        .background(Color.White)
                                        .weight(.4f)
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Fit
                                )
                                Box(
                                    modifier = Modifier
                                        .weight(.6f)
                                        .padding(12.dp)
                                        .fillMaxWidth()
                                ){
                                    Column {
                                        Column(Modifier.weight(.9f)) {
                                        Text(
                                            text = product.name,
                                            color = Color.Black,
                                            fontWeight = FontWeight.W500,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = product.type,
                                            modifier = Modifier
                                                .offset(y = -(8).dp),
                                            color = Color.Black.copy(alpha = .4f),
                                            fontWeight = FontWeight.W400,
                                            fontSize = 11.sp
                                        )
                                        Text(
                                            text = product.conversion!!,
                                            color = Color.Black.copy(alpha = .4f),
                                            fontWeight = FontWeight.W400,
                                            fontSize = 11.sp,
                                            lineHeight = 1.2.em
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            items(product.variations) { variation ->
                                                Box(
                                                    modifier = Modifier
                                                        .clickable {
                                                            selectedVariation = variation
                                                        }
                                                        .border(
                                                            width = 1.dp,
                                                            color = if (variation == selectedVariation) Color.LightGray else Color.Transparent,
                                                            shape = RoundedCornerShape(6.dp)
                                                        )
                                                ) {
                                                    Text(
                                                        text = variation,
                                                        modifier = Modifier
                                                            .padding(
                                                                horizontal = 8.dp,
                                                                vertical = 3.dp
                                                            ),
                                                        color = Color.Black,
                                                        fontWeight = FontWeight.W400,
                                                        fontSize = 11.sp
                                                    )
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = product.description,
                                            modifier = Modifier,
                                            color = Color.Black,
                                            fontWeight = FontWeight.W400,
                                            fontSize = 11.sp,
                                            lineHeight = 1.2.em
                                        )
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                        Row(
                                            verticalAlignment = Alignment.Bottom,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Row(Modifier.weight(.6f)) {
                                                Text(
                                                    text = "₱${product.pricePerPc.toInt()}",
                                                    modifier = Modifier,
                                                    color = Color.Black,
                                                    fontWeight = FontWeight.W600,
                                                    fontSize = 15.sp,
                                                )
                                                Text(
                                                    text = "/pc",
                                                    modifier = Modifier,
                                                    color = Color.Gray,
                                                    fontWeight = FontWeight.W400,
                                                    fontSize = 11.sp
                                                )
                                            }

                                            QuantityCounter()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Box (
                modifier = Modifier
                    .weight(.29f)
                    .fillMaxHeight()
                    .padding(top = 28.dp, start = 18.dp, end = 22.dp, bottom = 10.dp)
                    .border(1.dp, Color.LightGray.copy(alpha = .3f), RoundedCornerShape(8.dp))
            ){
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                ) {
                    Text(
                        text = "Order Details",
                        color = Color.Black,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(
                        color = Color.LightGray.copy(alpha = .6f),
                    )
                    Box(
                        modifier = Modifier
                            .weight(.6f)
                    )
                    HorizontalDivider(
                        color = Color.LightGray.copy(alpha = .6f),
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Payment Summary",
                        color = Color.Black,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp
                    )
                    Row {
                        Text(
                            text = "Items (0)",
                            modifier = Modifier
                                .weight(.9f),
                            color = Color.Black,
                            fontWeight = FontWeight.W400,
                            fontSize = 11.sp,
                        )

                        Text(
                            text = "₱0",
                            color = Color.Black,
                            fontWeight = FontWeight.W400,
                            fontSize = 11.sp,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clickable {  }
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(
                                color = Color.Gray.copy(alpha = .7f),
                                RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "₱0",
                                modifier = Modifier
                                    .weight(.9f),
                                color = Color.White,
                                fontWeight = FontWeight.W500,
                                fontSize = 13.sp,
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Proceed Order",
                                    color = Color.White,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 12.sp,
                                )
                                Spacer(Modifier.width(4.dp))
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowForward,
                                    contentDescription = "Filter",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
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