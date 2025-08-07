package com.example.cirrusmobileapp.data.mappers

import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import com.example.cirrusmobileapp.data.remote.dto.ProductDto
import com.example.cirrusmobileapp.domain.model.Product
import com.example.cirrusmobileapp.domain.model.Variant
import com.example.cirrusmobileapp.domain.model.WebSocketProduct

fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        brand = this.brand,
        imageUrl = this.imageUrl
    )
}

fun List<ProductDto>.toProductEntityList(): List<ProductEntity> {
    return this.map { it.toProductEntity() }
}

fun WebSocketProduct.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        brand = this.brand,
        imageUrl = this.imageUrl,
        // Map the nested list of variants from WebSocketVariant to Variant
        variants = this.variants.map { it.toVariant(productId = this.id) }
    )
}

fun ProductWithVariants.toDomainModel(): Product {
    return Product(
        id = this.product.id,
        name = this.product.name,
        description = this.product.description,
        category = this.product.category,
        brand = this.product.brand,
        imageUrl = this.product.imageUrl,
        variants = this.variants.map { it.toDomainVariant() }
    )
}

fun VariantEntity.toDomainVariant(): Variant {
    return Variant(
        variantId = this.variantId,
        productId = this.productId,
        variantName = this.variantName,
        singleName = this.singleName,
        description = this.description,
        pricePerVariantUnit = this.pricePerVariantUnit,
        skuPerVariantUnit = this.skuPerVariantUnit,
        imageUrl = this.imageUrl,
        stockQuantity = this.stockQuantity,
        unitOfMeasurement = this.unitOfMeasurement
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        category = this.category,
        brand = this.brand,
        imageUrl = this.imageUrl
    )
}