package com.example.cirrusmobileapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class VariantDto(
    @SerialName("id")
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
