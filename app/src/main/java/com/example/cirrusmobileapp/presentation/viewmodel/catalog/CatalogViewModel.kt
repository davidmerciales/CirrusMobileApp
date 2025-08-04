package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import com.example.cirrusmobileapp.presentation.screens.catalog.Product
import com.example.cirrusmobileapp.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
) : BaseViewModel<CatalogUiState, CatalogIntent>(CatalogUiState.Idle) {

    init {
        processIntent(CatalogIntent.LoadProducts)
    }

    override fun processIntent(intent: CatalogIntent) {
        when (intent) {
            is CatalogIntent.LoadProducts -> {
                loadProducts()
            }
        }
    }

    private fun loadProducts() {
        safeLaunch {
            delay(2000)
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
            _uiState.value = CatalogUiState.Success(products)
        }
    }

}
