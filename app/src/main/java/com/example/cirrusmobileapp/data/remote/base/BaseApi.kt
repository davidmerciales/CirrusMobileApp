package com.example.cirrusmobileapp.data.remote.base

import io.ktor.client.HttpClient
import javax.inject.Inject

abstract class BaseApi(
    protected val client: HttpClient,
    protected val baseUrl: String
) {
}