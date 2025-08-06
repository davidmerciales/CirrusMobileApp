package com.example.cirrusmobileapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ProductDto(
    @SerialName("_id")
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val brand: String,
    val imageUrl: String,
    val variants: List<VariantDto>
)

