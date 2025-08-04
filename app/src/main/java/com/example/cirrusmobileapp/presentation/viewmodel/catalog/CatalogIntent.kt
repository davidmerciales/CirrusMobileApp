package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import com.example.cirrusmobileapp.presentation.viewmodel.UiIntent

sealed class CatalogIntent : UiIntent {
    data object LoadProducts : CatalogIntent()
}