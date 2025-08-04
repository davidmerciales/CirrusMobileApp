package com.example.cirrusmobileapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val brand: String,
    val price: Double,
    val imageUrl: String,
    val unitOfMeasurement: String,
    val stockQuantity: Int,
    val variants: List<Variant>
)
