package com.example.cirrusmobileapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Variant(
    val id: String,
    val variantName: String,
    val singleName: String,
    val description: String,
    val pricePerVariantUnit: Double,
    val skuPerVariantUnit: String,
    val imageUrl: String,
    val stockQuantity: Int,
    val unitOfMeasurement: String
)