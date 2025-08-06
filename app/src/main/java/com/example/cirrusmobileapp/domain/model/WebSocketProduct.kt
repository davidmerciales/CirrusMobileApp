package com.example.cirrusmobileapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketProduct(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val brand: String,
    val imageUrl: String,
    val variants: List<WebSocketVariant>)
