package com.example.cirrusmobileapp.data.remote.api

import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.base.BaseApiResponse
import com.example.cirrusmobileapp.data.remote.dto.ProductDto

interface ProductApiService {
    suspend fun fetchProducts(lastSyncedDate: String? = null): ApiResult<BaseApiResponse<List<ProductDto>>>
}