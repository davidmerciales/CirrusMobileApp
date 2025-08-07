package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import BaseViewModel
import androidx.lifecycle.viewModelScope
import com.example.cirrusmobileapp.data.mappers.toDomainModel
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel<CatalogContract.State, CatalogContract.Intent, CatalogContract.Effect>() {

    private val _searchQuery = MutableStateFlow("")

    init {
        setupProductsFlow()
    }

    override fun createInitialState() = CatalogContract.State(isLoading = true)

    override fun handleIntent(intent: CatalogContract.Intent) {
        when (intent) {
            CatalogContract.Intent.LoadProducts -> {
               // refreshProducts()
            }
            is CatalogContract.Intent.SearchProducts -> {
                updateSearchQuery(intent.query)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun setupProductsFlow() {
        viewModelScope.launch {
            _searchQuery
                .flatMapLatest { query ->
                    if (query.isEmpty()) {
                        productRepository.getProducts()
                    } else {
                        productRepository.searchProducts(query)
                    }
                }
                .collectLatest { productsWithVariants ->
                    val domainProducts = productsWithVariants.map { it.toDomainModel() }
                    setState {
                        copy(
                            products = domainProducts,
                            isLoading = false,
                            isRefreshing = false,
                            error = null
                        )
                    }
                }
        }
    }

    private fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        setState { copy(isLoading = true, error = null) }
    }

//    private fun refreshProducts() {
//        safeLaunch(
//            onError = { throwable ->
//                setState { copy(isLoading = false, isRefreshing = false) }
//                setEffect(CatalogContract.Effect.ShowError(throwable.message ?: "An unknown error occurred."))
//            }
//        ) {
//            setState { copy(isRefreshing = true) }
//            productRepository.refreshProducts()
//            setState { copy(isRefreshing = false) }
//        }
//    }

    override fun Throwable.toErrorEffect(): CatalogContract.Effect {
        return CatalogContract.Effect.ShowError(message ?: "An unexpected error occurred")
    }
}