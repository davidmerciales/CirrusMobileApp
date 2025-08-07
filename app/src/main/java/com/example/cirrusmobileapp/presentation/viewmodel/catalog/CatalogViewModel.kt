package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import BaseViewModel
import androidx.lifecycle.viewModelScope
import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import com.example.cirrusmobileapp.data.mappers.toDomainModel
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.model.Variant
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel<CatalogUiState, CatalogIntent>() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val uiState: StateFlow<CatalogUiState> = searchQuery
        .flatMapLatest { query: String ->
            val productsFlow: Flow<List<ProductWithVariants>> = if (query.isEmpty()) {
                productRepository.getProducts()
            } else {
                productRepository.searchProducts(query)
            }
            productsFlow
        }
        .map { productsWithVariants ->
            val domainProducts = productsWithVariants.map { it.toDomainModel() }
            CatalogUiState.Success(products = domainProducts)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CatalogUiState.Loading
        )

    init {
        refreshProducts()
    }

    override fun processIntent(intent: CatalogIntent) {
        when (intent) {
            is CatalogIntent.LoadProducts -> {
                refreshProducts()
            }
            is CatalogIntent.SearchProducts -> {
                updateSearchQuery(intent.query)
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

    fun refreshProducts() {
        safeLaunch {
            productRepository.refreshProducts()
        }
    }
}