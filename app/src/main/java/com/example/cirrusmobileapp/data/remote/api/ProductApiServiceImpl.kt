package com.example.cirrusmobileapp.data.remote.api

import com.example.cirrusmobileapp.data.remote.base.ApiResult
import com.example.cirrusmobileapp.data.remote.base.BaseApi
import com.example.cirrusmobileapp.data.remote.base.BaseApiResponse
import com.example.cirrusmobileapp.data.remote.dto.ProductDto
import com.example.cirrusmobileapp.data.remote.base.safeApiCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import javax.inject.Inject

class ProductApiServiceImpl @Inject constructor(
    client: HttpClient,
    baseUrl: String
): BaseApi(client, baseUrl), ProductApiService {
    override suspend fun fetchProducts(lastSyncedDate: String?, page : Int?, size : Int?): ApiResult<BaseApiResponse<List<ProductDto>>> {
        return safeApiCall {
            client.get{
                url{
                    takeFrom(baseUrl)
                    encodedPath = "api/app/products/"
                }
                if (lastSyncedDate != null) {
                    parameter("lastSyncedDate", lastSyncedDate)
                    parameter("limit", size)
                    parameter("offset", page)
                }
            }.body()
        }
    }
}