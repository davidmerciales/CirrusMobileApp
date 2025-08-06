package com.example.cirrusmobileapp.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "variants",
    foreignKeys = [ForeignKey(
        entity = ProductEntity::class,
        parentColumns = ["product_id"],
        childColumns = ["productId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class VariantEntity(
    @PrimaryKey
    val variantId: String,
    val productId: String,
    val variantName: String,
    val singleName: String,
    val description: String,
    val pricePerVariantUnit: Double,
    val skuPerVariantUnit: String,
    val imageUrl: String,
    val stockQuantity: Int,
    val unitOfMeasurement: String
)