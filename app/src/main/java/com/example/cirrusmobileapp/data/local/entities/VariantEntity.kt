package com.example.cirrusmobileapp.data.local.entities

data class VariantEntity(
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