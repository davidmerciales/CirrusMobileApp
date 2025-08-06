package com.example.cirrusmobileapp.data.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.local.entities.VariantEntity

data class ProductWithVariants(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "productId"
    )
    val variants: List<VariantEntity>
)
