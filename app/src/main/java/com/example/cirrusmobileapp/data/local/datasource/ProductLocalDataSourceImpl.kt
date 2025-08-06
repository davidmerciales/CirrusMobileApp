package com.example.cirrusmobileapp.data.local.datasource

import com.example.cirrusmobileapp.data.local.dao.ProductDao
import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductLocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
): ProductLocalDataSource {
    override fun getProducts(): Flow<List<ProductEntity>> {
        return productDao.getProducts()
    }

    override suspend fun getProductById(id: Int): ProductEntity? {
        return productDao.getProductById(id)
    }

    override suspend fun deleteProductById(id: Int) {
        productDao.deleteProductById(id)
    }

    override suspend fun deleteAllProducts() {
        productDao.deleteAllProducts()
    }

    override fun insertProduct(product: ProductEntity) {
        productDao.insertProduct(product)
    }

    override suspend fun insertAllProducts(products: List<ProductEntity>) {
        productDao.insertAllProducts(products)
    }
}