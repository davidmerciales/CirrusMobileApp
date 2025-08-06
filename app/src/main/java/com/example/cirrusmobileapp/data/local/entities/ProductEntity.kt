package com.example.cirrusmobileapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "product_id")
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val brand: String,
    val imageUrl: String,
    val variantEntities: List<VariantEntity> // This requires a TypeConverter
)