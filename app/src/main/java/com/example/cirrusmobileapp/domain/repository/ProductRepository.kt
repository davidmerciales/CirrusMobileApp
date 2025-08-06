package com.example.cirrusmobileapp.domain.repository

import com.example.cirrusmobileapp.data.local.entities.ProductEntity
import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.base.BaseApiResponse
import com.example.cirrusmobileapp.data.remote.dto.ProductDto
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<List<ProductEntity>>
    suspend fun fetchProducts(lastSyncedDate: String?): ApiResult<BaseApiResponse<List<ProductDto>>>
}