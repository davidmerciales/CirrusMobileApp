package com.example.cirrusmobileapp.presentation.screens.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun QuantityCounter(
    modifier: Modifier = Modifier,
    onCountChanged: (Int) -> Unit = {},
    initialCount: Int = 0
) {
    var count by remember { mutableStateOf(initialCount) }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .height(30.dp)
            .width(80.dp)
            .border(1.dp, Color.LightGray.copy(alpha = .6f), RoundedCornerShape(20.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                if (count > 0) {
                    count--
                    onCountChanged(count)
                }
            },
            modifier = Modifier
                .padding(start = 4.dp)
                .size(20.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Gray.copy(alpha = 0.3f),
                contentColor = Color.Black
            )
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease quantity"
            )
        }

        Text(
            text = count.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        )

        IconButton(
            onClick = {
                count++
                onCountChanged(count)
            },
            modifier = Modifier
                .padding(end = 4.dp)
                .size(20.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase quantity"
            )
        }
    }
}
