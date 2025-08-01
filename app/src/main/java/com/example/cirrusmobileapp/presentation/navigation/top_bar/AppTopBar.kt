package com.example.cirrusmobileapp.presentation.navigation.top_bar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cirrusmobileapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    Surface(
        shadowElevation = 4.dp
    ) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .offset(y = -(8).dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.jdi_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(52.dp)
                )
                Text(
                    text = "Jardine Distribution, Inc.",
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        },
        actions = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .offset(y = -(8).dp),
            ) {
                Icon(
                    imageVector = Icons.Default.NotificationsNone,
                    contentDescription = "Notifications",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(23.dp)
                        .clickable { }
                )
                ProfileDropdown()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,

        )
    )
}
    }