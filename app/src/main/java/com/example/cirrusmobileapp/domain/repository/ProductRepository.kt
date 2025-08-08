package com.example.cirrusmobileapp.domain.repository

import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.model.Variant
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<ProductWithVariants>>
    fun refreshProducts(): Flow<SyncProgress>
    suspend fun upsertProductAndVariants(product: Product, variants: List<Variant>)
    fun searchProducts(query: String): Flow<List<ProductWithVariants>>
}

data class SyncProgress(
    val progress: Int,
    val message: String,
    val isComplete: Boolean
)