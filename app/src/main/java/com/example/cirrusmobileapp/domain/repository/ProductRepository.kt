package com.example.cirrusmobileapp.domain.repository

import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.model.Variant
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<ProductWithVariants>>
    suspend fun refreshProducts(lastSyncedDate: String?)
    suspend fun upsertProductAndVariants(product: Product, variants: List<Variant>)
    fun searchProducts(query: String): Flow<List<ProductWithVariants>>
}