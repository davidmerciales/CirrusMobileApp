package com.example.cirrusmobileapp.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import org.json.JSONObject

@Serializable
data class WebSocketPayload(
    val entity: String,
    val event: String,
    val data: JsonObject,
    val changes: JsonObject? = null
)