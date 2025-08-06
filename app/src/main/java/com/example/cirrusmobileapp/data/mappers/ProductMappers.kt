package com.example.cirrusmobileapp.data.mappers

import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.remote.dto.ProductDto
import com.example.cirrusmobileapp.domain.model.Product

fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        brand = this.brand,
        imageUrl = this.imageUrl,
        variantEntities = this.variants.map { it.toLocalVariant() }
    )
}

fun ProductEntity.toDomainModel(): Product {
    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        brand = this.brand,
        imageUrl = this.imageUrl,
        variants = this.variantEntities.map { it.toDomainVariant() }
    )
}

fun ProductDto.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        brand = this.brand,
        imageUrl = this.imageUrl,
        variants = this.variants.map { it.toVariant() }
    )
}
