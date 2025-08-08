package com.example.cirrusmobileapp.data.remote.datasource

import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.api.ProductApiService
import com.example.cirrusmobileapp.data.remote.base.BaseApiResponse
import com.example.cirrusmobileapp.data.remote.dto.ProductDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRemoteDataSourceImpl @Inject constructor(
    private val productApiService: ProductApiService
): ProductRemoteDataSource  {
    override suspend fun fetchProducts(lastSyncDate: String?, page: Int?, size: Int?,): ApiResult<BaseApiResponse<List<ProductDto>>> {
        return productApiService.fetchProducts(lastSyncDate, page, size)
    }
}