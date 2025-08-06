package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.presentation.viewmodel.UiState

sealed class CatalogUiState : UiState {
    object Idle : CatalogUiState()
    data class Success(val products: List<Product>) : CatalogUiState()
    data class Error(val message: String) : CatalogUiState()
}
