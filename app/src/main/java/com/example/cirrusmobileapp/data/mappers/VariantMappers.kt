package com.example.cirrusmobileapp.data.mappers

import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.example.cirrusmobileapp.data.remote.dto.VariantDto
import com.example.cirrusmobileapp.domain.model.Variant as DomainVariant

fun VariantEntity.toDomainVariant(): DomainVariant {
    return DomainVariant(
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

fun VariantDto.toLocalVariant(): VariantEntity {
    return VariantEntity(
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


fun VariantDto.toVariant(): DomainVariant {
    return DomainVariant(
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
