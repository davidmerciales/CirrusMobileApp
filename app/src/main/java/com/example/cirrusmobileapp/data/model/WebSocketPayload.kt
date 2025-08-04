package com.example.cirrusmobileapp.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class WebSocketPayload(
    val type: String,
    val action: String,
    val entityId: String,
    val data: JsonObject,
    val timestamp: String,
    val message: String
)