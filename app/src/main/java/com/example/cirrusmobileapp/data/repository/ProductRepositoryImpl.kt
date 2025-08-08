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
import com.example.cirrusmobileapp.domain.repository.SyncProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.roundToInt

class ProductRepositoryImpl @Inject constructor(
    private val prefDataStore: PreferencesManager,
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override fun getProducts(): Flow<List<ProductWithVariants>> {
        return productLocalDataSource.getProductsWithVariants()
    }

    override fun refreshProducts(): Flow<SyncProgress> = flow {
        val batchSize = 20000
        var totalFetched = 0
        var totalCount = 0
        var page = 1

        val effectiveSyncDate = prefDataStore.getString("last_synced_date") ?: ""

        try {
            emit(SyncProgress(progress = 0, message = "Starting sync...", isComplete = false))

            do {
                val remoteResponse = productRemoteDataSource.fetchProducts(
                    lastSyncedDate = effectiveSyncDate,
                    page = page,
                    size = batchSize,
                )

                when (remoteResponse) {
                    is ApiResult.Success -> {
                        val productsDto = remoteResponse.data.data
                        val fetchedCount = productsDto?.size ?: 0

                        if (totalCount == 0) {
                            totalCount = remoteResponse.data.meta?.totalCount ?: 0
                            Log.d("Sync_date", "📊 Total products to sync: $totalCount")
                        }
                        Log.d("Sync_date", "✅ Fetched $fetchedCount products for page $page")

                        productsDto?.let { productsDtoList ->
                            val productEntities = productsDtoList.toProductEntityList()
                            val variantEntities = productsDtoList.flatMap { productDto ->
                                productDto.variants.map { it.toVariantEntity(productDto.id) }
                            }
                            productLocalDataSource.insertAllProduct(productEntities)
                            productLocalDataSource.insertAllVariant(variantEntities)
                        }

                        totalFetched += fetchedCount

                        val progress = if (totalCount > 0) {
                            ((totalFetched.toFloat() / totalCount) * 100f).roundToInt()
                        } else 0

                        val message = "Syncing... $totalFetched of $totalCount products"
                        emit(SyncProgress(progress, message, false))

                        page++
                    }

                    is ApiResult.Error -> throw Exception("API Error: ${remoteResponse.message}")
                    is ApiResult.NetworkError -> throw remoteResponse.throwable
                }
            } while (remoteResponse is ApiResult.Success && (remoteResponse.data.data?.size ?: 0) == batchSize)

            val lastSyncedDate = System.currentTimeMillis().toIsoDateTime()
            prefDataStore.saveString("last_synced_date", lastSyncedDate)
            Log.d("Sync_date", "✅ Sync completed. Total: $totalFetched products")
            emit(SyncProgress(progress = 100, message = "Sync complete!", isComplete = true))

        } catch (e: Exception) {
            Log.e("Sync_date", "💥 Sync failed", e)
            emit(SyncProgress(progress = 0, message = "Sync failed: ${e.message}", isComplete = false))
            throw e
        }
    }

    override fun searchProducts(query: String): Flow<List<ProductWithVariants>> {
        return productLocalDataSource.searchProductsWithVariants(query)
    }

    override suspend fun upsertProductAndVariants(product: Product, variants: List<Variant>) {
        val productEntity = product.toProductEntity()
        val variantEntities = variants.map { it.toVariantEntity(productEntity.id) }
        productLocalDataSource.upsertProduct(productEntity)
        productLocalDataSource.upsertAllVariants(variantEntities)
    }
}