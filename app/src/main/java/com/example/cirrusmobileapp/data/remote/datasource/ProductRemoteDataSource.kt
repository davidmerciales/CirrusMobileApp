package com.example.cirrusmobileapp.data.remote.datasource

import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.base.BaseApiResponse
import com.example.cirrusmobileapp.data.remote.dto.ProductDto

interface ProductRemoteDataSource {
    suspend fun fetchProducts(lastSyncedDate: String?): ApiResult<BaseApiResponse<List<ProductDto>>>
}