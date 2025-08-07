package com.example.cirrusmobileapp.presentation.screens.catalog.sections

import ShimmerProvider
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cirrusmobileapp.presentation.common.search_bar.CommonSearchBar
import com.example.cirrusmobileapp.presentation.screens.catalog.ProductCardItem
import com.example.cirrusmobileapp.presentation.viewmodel.catalog.CatalogContract
import com.example.cirrusmobileapp.presentation.viewmodel.catalog.CatalogViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductListSection(
    modifier: Modifier
) {
    val catalogViewModel: CatalogViewModel = hiltViewModel()
    val state by catalogViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        catalogViewModel.effect.collectLatest { effect ->
            when (effect) {
                is CatalogContract.Effect.ShowError -> {
                    println("Error: ${effect.message}")
                }
            }
        }
    }

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CommonSearchBar(
                modifier = Modifier
                    .weight(.85f)
                    .height(40.dp),
                placeholder = "Search product",
                onSearch = {
                    catalogViewModel.processIntent(
                        CatalogContract.Intent.SearchProducts(it)
                    )
                }
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
            if(state.isLoading){
                items(3) { product ->
                    ShimmerProvider {
                        ProductCardItem(
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
                                ),
                        )
                    }

                }
            } else if(state.products.isNotEmpty()){
                items(
                    key = { product -> product.id },
                    items = state.products) { product ->
                    ProductCardItem(
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
                            ),
                        product
                    )
                }
            } else {
                item {
                    Text(text = "No products found.")
                }
            }

        }

    }
}
