package com.example.cirrusmobileapp.data.local.datasource

import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {
    fun getProducts(): Flow<List<ProductEntity>>

    suspend fun getProductById(id: Int): ProductEntity?

    suspend fun deleteProductById(id: Int)

    suspend fun deleteAllProducts()

    fun insertProduct(product: ProductEntity)

    suspend fun insertAllProducts(products: List<ProductEntity>)
}