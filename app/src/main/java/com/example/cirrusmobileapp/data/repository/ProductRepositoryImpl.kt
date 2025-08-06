package com.example.cirrusmobileapp.data.repository

import com.example.cirrusmobileapp.data.local.datasource.ProductLocalDataSource
import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.base.BaseApiResponse
import com.example.cirrusmobileapp.data.remote.datasource.ProductRemoteDataSource
import com.example.cirrusmobileapp.data.remote.dto.ProductDto
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productLocalDataSource : ProductLocalDataSource,
    private val productRemoteDataSource : ProductRemoteDataSource
): ProductRepository {

    override fun getProducts(): Flow<List<ProductEntity>> {
        return productLocalDataSource.getProducts()
    }

    override suspend fun fetchProducts(lastSyncedDate: String?): ApiResult<BaseApiResponse<List<ProductDto>>> {
        return productRemoteDataSource.fetchProducts(lastSyncedDate)
    }
}