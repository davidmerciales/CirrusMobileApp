package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import UiState
import com.example.cirrusmobileapp.domain.model.Product

sealed class CatalogUiState : UiState {
    object Loading : CatalogUiState()
    data class Success(val products: List<Product>) : CatalogUiState()
    data class Error(val message: String) : CatalogUiState()
}