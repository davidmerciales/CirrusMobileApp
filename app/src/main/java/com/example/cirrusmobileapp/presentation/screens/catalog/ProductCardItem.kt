package com.example.cirrusmobileapp.presentation.screens.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.cirrusmobileapp.R
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.model.Variant
import java.text.NumberFormat
import java.util.Locale
import shimmerable

@Composable
fun ProductCardItem(
    modifier: Modifier,
    product: Product? = null
) {
    // Correctly initialize state with the full Variant object
    var selectedVariation by remember { mutableStateOf(product?.variants?.firstOrNull()) }

    Box(
        modifier = modifier
    ){
        Column {
            Image(
                painter = painterResource(id = R.drawable.sample_product_1),
                contentDescription = "App Logo",
                modifier = Modifier
                    .background(Color.White)
                    .weight(.4f)
                    .fillMaxWidth()
                    .shimmerable(),
                contentScale = ContentScale.Fit
            )
            Box(
                modifier = Modifier
                    .weight(.6f)
                    .padding(12.dp)
                    .fillMaxWidth()
                    .shimmerable()
            ){
                Column {
                    Column(Modifier.weight(.9f)) {
                        Text(
                            text = product?.name ?: "",
                            color = Color.Black,
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp,
                            modifier = Modifier
                        )
                        Text(
                            text = product?.category ?:"",
                            modifier = Modifier
                                .offset(y = -(8).dp)
                                .shimmerable(),
                            color = Color.Black.copy(alpha = .4f),
                            fontWeight = FontWeight.W400,
                            fontSize = 11.sp,
                        )
                        Text(
                            text = product?.brand ?: "",
                            color = Color.Black.copy(alpha = .4f),
                            fontWeight = FontWeight.W400,
                            fontSize = 11.sp,
                            lineHeight = 1.2.em,
                            modifier = Modifier
                                .shimmerable()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (product?.variants == null) { return@LazyRow }
                            items(product.variants) { variation ->
                                Box(
                                    modifier = Modifier
                                        .clickable {
                                            // Set the state to the full Variant object
                                            selectedVariation = variation
                                        }
                                        .border(
                                            width = 1.dp,
                                            // Compare the full Variant objects
                                            color = if (variation == selectedVariation) Color.LightGray else Color.Transparent,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                ) {
                                    Text(
                                        // Display the variant name property
                                        text = variation.variantName,
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
                            text = product?.description ?: "",
                            color = Color.Black,
                            fontWeight = FontWeight.W400,
                            fontSize = 11.sp,
                            lineHeight = 1.2.em,
                            modifier = Modifier
                                .shimmerable()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(Modifier.weight(.6f)) {
                            val price = selectedVariation?.pricePerVariantUnit ?: 0.0
                            val currencyFormatter = remember { NumberFormat.getCurrencyInstance(Locale("en", "PH")) }
                            Text(
                                text = currencyFormatter.format(price),
                                modifier = Modifier
                                    .shimmerable(),
                                color = Color.Black,
                                fontWeight = FontWeight.W600,
                                fontSize = 15.sp,
                            )
                            Text(
                                text = "/pc",
                                modifier = Modifier
                                    .shimmerable(),
                                color = Color.Gray,
                                fontWeight = FontWeight.W400,
                                fontSize = 11.sp
                            )
                        }

                        QuantityCounter(
                            onCountChange = {

                            }
                        )
                    }
                }
            }
        }
    }

}