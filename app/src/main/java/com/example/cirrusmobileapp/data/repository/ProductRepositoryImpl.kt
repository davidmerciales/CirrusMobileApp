package com.example.cirrusmobileapp.data.repository

import android.util.Log
import com.example.cirrusmobileapp.common.ext.toIsoDateTime
import com.example.cirrusmobileapp.data.local.datasource.ProductLocalDataSource
import com.example.cirrusmobileapp.data.local.datasource.shared_pref_datastore.PreferencesManager
import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import com.example.cirrusmobileapp.data.mappers.toLocalEntity
import com.example.cirrusmobileapp.data.mappers.toProductEntity
import com.example.cirrusmobileapp.data.mappers.toProductEntityList
import com.example.cirrusmobileapp.data.mappers.toVariantEntity
import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.datasource.ProductRemoteDataSource
import com.example.cirrusmobileapp.data.remote.dto.VariantDto
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.model.Variant
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val prefDataStore: PreferencesManager,
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<ProductWithVariants>> {
        return productLocalDataSource.getProductsWithVariants()
    }

    override suspend fun refreshProducts() {
        Log.d("Sync_date", "🔄 Starting product refresh")

        val effectiveSyncDate = prefDataStore.getString("last_synced_date")
            ?: ""

        Log.d("Sync_date", "📦 Effective sync date: $effectiveSyncDate")

        val remoteResponse = productRemoteDataSource.fetchProducts(effectiveSyncDate)

        when (remoteResponse) {
            is ApiResult.Success -> {
                val productsDto = remoteResponse.data.data
                Log.d("Sync_date", "✅ Successfully fetched ${productsDto?.size ?: 0} products")


                productsDto?.let { productsDtoList ->
                    productLocalDataSource.insertAllProduct(productsDtoList.toProductEntityList())

                    val allVariantEntities = productsDtoList.flatMap { productDto ->
                        productDto.variants.map { it.toVariantEntity(productDto.id) }
                    }

                    productLocalDataSource.insertAllVariant(allVariantEntities)
                }

                val lastSyncedDate = System.currentTimeMillis().toIsoDateTime()
                prefDataStore.saveString("last_synced_date", lastSyncedDate)
                Log.d("Sync_date", "🕒 Saved last synced date: $lastSyncedDate")
            }

            is ApiResult.Error -> {
                Log.e("Sync_date", "❌ Error while fetching products: ${remoteResponse.message}")
                throw Exception(remoteResponse.message)
            }

            is ApiResult.NetworkError -> {
                Log.e("Sync_date", "🌐 Network error during product fetch", remoteResponse.throwable)
                throw remoteResponse.throwable
            }
        }
    }

    override fun searchProducts(query: String): Flow<List<ProductWithVariants>> {
        return productLocalDataSource.searchProductsWithVariants(query)
    }

    override suspend fun upsertProductAndVariants(product: Product, variants: List<Variant>) {
        val productEntity = product.toProductEntity()
        val variantEntities = variants.map { it.toVariantEntity(productEntity.id) } // Assuming you have this mapper
        productLocalDataSource.upsertProduct(productEntity)
        productLocalDataSource.upsertAllVariants(variantEntities)
    }
}