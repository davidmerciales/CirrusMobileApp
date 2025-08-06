package com.example.cirrusmobileapp.data.remote.base

import io.ktor.client.HttpClient
import javax.inject.Inject

abstract class BaseApi(
    protected val client: HttpClient,
    protected val baseUrl: String
) {
    // You can add generic methods here for common tasks
    // For example, a method for making requests with a specific HTTP method
    // Or a method for handling authentication headers
}