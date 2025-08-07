package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import UiEffect
import UiIntent
import UiState
import com.example.cirrusmobileapp.domain.model.Product

object CatalogContract {

    sealed class Intent : UiIntent {
        data object LoadProducts : Intent()
        data class SearchProducts(val query: String) : Intent()
    }

    data class State(
        val products: List<Product> = emptyList(),
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val error: String? = null
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowError(val message: String) : Effect()
    }
}