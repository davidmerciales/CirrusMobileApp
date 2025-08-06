package com.example.cirrusmobileapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("Select * from products")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("Select * from products where product_id = :id")
    suspend fun getProductById(id: Int): ProductEntity?

    @Query("Delete from products where product_id = :id")
    suspend fun deleteProductById(id: Int)

    @Query("Delete from products")
    suspend fun deleteAllProducts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductEntity>)
}