package com.example.cirrusmobileapp.data.local.datasource

import com.example.cirrusmobileapp.data.local.dao.ProductDao
import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.local.entities.VariantEntity
import com.example.cirrusmobileapp.data.local.model.ProductWithVariants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : ProductLocalDataSource {
    override fun getProductsWithVariants(): Flow<List<ProductWithVariants>> {
        return productDao.getProductsWithVariants()
    }

    override suspend fun getProductWithVariantsById(id: String): ProductWithVariants? {
        return productDao.getProductWithVariantsById(id)
    }

    override fun searchProductsWithVariants(query: String): Flow<List<ProductWithVariants>> {
        return productDao.searchProductsWithVariants(query)
    }

    override suspend fun deleteProductById(id: String) {
        productDao.deleteProductById(id)
    }

    override suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }

    override suspend fun upsertProduct(product: ProductEntity) {
        productDao.upsertProduct(product)
    }

    override suspend fun insertAllProduct(productList: List<ProductEntity>) {
        productDao.insertAllProduct(productList)
    }

    override suspend fun insertAllVariant(variantList: List<VariantEntity>) {
        productDao.insertAllVariant(variantList)
    }

    override suspend fun upsertAllProducts(products: List<ProductEntity>) {
        productDao.upsertAllProducts(products)
    }

    override suspend fun upsertVariant(variant: VariantEntity) {
        productDao.upsertVariant(variant)
    }

    override suspend fun upsertAllVariants(variants: List<VariantEntity>) {
        productDao.upsertAllVariants(variants)
    }
}