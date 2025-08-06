package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import UiIntent

sealed class CatalogIntent : UiIntent {
    data object LoadProducts : CatalogIntent()
    data class SearchProducts(val query: String) : CatalogIntent()
}