package com.example.cirrusmobileapp.data.remote.base

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall.invoke())
    } catch (e: RedirectResponseException) {
        ApiResult.Error(e.response.status.value, e.response.status.description)
    } catch (e: ClientRequestException) {
        ApiResult.Error(e.response.status.value, e.response.status.description)
    } catch (e: ServerResponseException) {
        ApiResult.Error(e.response.status.value, e.response.status.description)
    } catch (e: Exception) {
        ApiResult.NetworkError(e)
    }
}