package com.example.cirrusmobileapp.data.local.datasource

import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {
    fun getProductsWithVariants(): Flow<List<ProductWithVariants>>

    suspend fun getProductWithVariantsById(id: String): ProductWithVariants?

    fun searchProductsWithVariants(query: String): Flow<List<ProductWithVariants>>

    suspend fun deleteProductById(id: String)

    suspend fun deleteAllProducts()

    suspend fun upsertProduct(product: ProductEntity)

    suspend fun insertAllProduct(productList: List<ProductEntity>)
    suspend fun insertAllVariant(variantList: List<VariantEntity>)

    suspend fun insertAllProductAllVariant(productList: List<ProductEntity>, variantList: List<VariantEntity>)

    suspend fun upsertAllProducts(products: List<ProductEntity>)

    suspend fun upsertVariant(variant: VariantEntity)

    suspend fun upsertAllVariants(variants: List<VariantEntity>)
}