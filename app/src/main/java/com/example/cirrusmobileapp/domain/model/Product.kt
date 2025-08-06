package com.example.cirrusmobileapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val brand: String,
    val imageUrl: String,
    val variants: List<Variant>)
