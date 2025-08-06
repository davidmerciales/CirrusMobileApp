package com.example.cirrusmobileapp.data.repository

import com.example.cirrusmobileapp.data.local.datasource.ProductLocalDataSource
import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import com.example.cirrusmobileapp.data.mappers.toLocalEntity
import com.example.cirrusmobileapp.data.mappers.toProductEntity
import com.example.cirrusmobileapp.data.mappers.toVariantEntity
import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.datasource.ProductRemoteDataSource
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.model.Variant
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<ProductWithVariants>> {
        return productLocalDataSource.getProductsWithVariants()
    }

    override suspend fun refreshProducts(lastSyncedDate: String?) {
        val remoteResponse = productRemoteDataSource.fetchProducts(lastSyncedDate)
        when (remoteResponse) {
            is ApiResult.Success -> {
                val productsDto = remoteResponse.data.data
                productsDto?.forEach { productDto ->
                    val productEntity = productDto.toProductEntity()
                    val variantEntities = productDto.variants.map { variantDto ->
                        variantDto.toLocalEntity(productId = productEntity.id)
                    }
                    productLocalDataSource.upsertProduct(productEntity)
                    productLocalDataSource.upsertAllVariants(variantEntities)
                }
            }

            is ApiResult.Error -> {
                throw Exception(remoteResponse.message)
            }

            is ApiResult.NetworkError -> {
                throw remoteResponse.throwable
            }
        }
    }

    override fun searchProducts(query: String): Flow<List<ProductWithVariants>> {
        return productLocalDataSource.searchProductsWithVariants(query)
    }

    override suspend fun upsertProductAndVariants(product: Product, variants: List<Variant>) {
        val productEntity = product.toProductEntity()
        val variantEntities = variants.map { it.toVariantEntity() } // Assuming you have this mapper
        productLocalDataSource.upsertProduct(productEntity)
        productLocalDataSource.upsertAllVariants(variantEntities)
    }
}