package com.example.cirrusmobileapp.data.remote.base

import kotlinx.serialization.Serializable

@Serializable
data class BaseApiResponse<T>(
    val status: Int,
    val data: T?,
    val message: String?
)
