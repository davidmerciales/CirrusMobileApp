package com.example.cirrusmobileapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.cirrusmobileapp.common.ext.chunkedInsert
import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Transaction
    @Query("SELECT * FROM products")
    fun getProductsWithVariants(): Flow<List<ProductWithVariants>>

    @Transaction
    @Query("SELECT * FROM products WHERE product_id = :id")
    suspend fun getProductWithVariantsById(id: String): ProductWithVariants?

    @Transaction
    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchProductsWithVariants(query: String): Flow<List<ProductWithVariants>>

    @Query("DELETE FROM products WHERE product_id = :id")
    suspend fun deleteProductById(id: String)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Upsert
    suspend fun upsertProduct(product: ProductEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllProduct(productList: List<ProductEntity>)

    @Insert(onConflict = REPLACE)
    suspend fun insertAllVariant(variantList: List<VariantEntity>)

    @Transaction
    suspend fun insertProductWithVariants(productEntityList: List<ProductEntity>, variantEntityList: List<VariantEntity>) {
        insertAllProduct(productEntityList)
        insertAllVariant(variantEntityList)
    }

    @Upsert
    suspend fun upsertAllProducts(products: List<ProductEntity>)

    @Upsert
    suspend fun upsertVariant(variant: VariantEntity)

    @Upsert
    suspend fun upsertAllVariants(variants: List<VariantEntity>)
}