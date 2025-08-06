package com.example.cirrusmobileapp.presentation.viewmodel.catalog

import com.example.cirrusmobileapp.data.mappers.toProduct
import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import com.example.cirrusmobileapp.presentation.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository
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
            val response = productRepository.fetchProducts("2025-08-04T15:09:55")

            when (response) {
                is ApiResult.Success -> {
                    val baseResponse = response.data
                    if (baseResponse.status == 200 && baseResponse.data != null) {
                        val products: List<Product> = baseResponse.data.map { it.toProduct() }
                        _uiState.value = CatalogUiState.Success(products)
                    } else {
                        _uiState.value =
                            CatalogUiState.Error(baseResponse.message ?: "Unknown API error")
                    }
                }

                is ApiResult.Error -> {
                    CatalogUiState.Error(response.message ?: "Unknown API error")

                }

                is ApiResult.NetworkError -> {
                    CatalogUiState.Error(response.throwable.message ?: "Unknown API error")
                }
            }

        }
    }

}
