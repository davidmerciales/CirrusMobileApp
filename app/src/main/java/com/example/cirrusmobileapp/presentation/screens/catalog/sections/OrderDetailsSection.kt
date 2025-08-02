package com.example.cirrusmobileapp.presentation.screens.catalog.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderDetailsSection(
    modifier: Modifier
) {

    Box (
        modifier = modifier
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
                        RoundedCornerShape(6.dp)
                    ),
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