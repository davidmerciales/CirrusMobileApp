package com.example.cirrusmobileapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cirrusmobileapp.data.model.WebSocketPayload
import com.example.cirrusmobileapp.data.websocket.WebSocketServiceImpl
import com.example.cirrusmobileapp.domain.websocket.WebSocketEvent
import com.example.cirrusmobileapp.domain.websocket.WebSocketService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

enum class NotificationType {
    CREATED, UPDATED, DELETED, INFO
}

data class AppNotification(
    val title: String,
    val description: String,
    val type: NotificationType = NotificationType.INFO,
    val timestamp: Long = System.currentTimeMillis()
)

data class NotificationEvent(val title: String, val message: String)

@HiltViewModel
class AppViewModel @Inject constructor(
    private val json: Json,
    private val webSocketService: WebSocketService
) : ViewModel() {
    private val _notificationEvents = MutableSharedFlow<AppNotification>()
    val notificationEvents: SharedFlow<AppNotification> = _notificationEvents.asSharedFlow()

    init {
        webSocketService.connect { event ->
            when (event) {
                is WebSocketEvent.OnOpen -> Log.d("WebSocket", "Connected!")
                is WebSocketEvent.OnMessage -> {
                    Log.d("WebSocket", "Received: ${event.message}")
                    handleWebSocketPayload(event.message)
                }
                is WebSocketEvent.OnFailure -> Log.e("WebSocket", "Error: ${event.throwable}")
                is WebSocketEvent.OnClosed -> Log.d("WebSocket", "Closed")
            }
        }
    }


    fun showNotification(
        title: String,
        description: String,
        type: NotificationType = NotificationType.INFO
    ) {
        viewModelScope.launch {
            _notificationEvents.emit(
                AppNotification(
                    title = title,
                    description = description,
                    type = type
                )
            )
        }
    }

    fun sendMessage(msg: String) {
        webSocketService.send(msg)
    }

    override fun onCleared() {
        super.onCleared()
        webSocketService.disconnect()
    }

    fun handleWebSocketPayload(message: String) {
        try {
            val payload = json.decodeFromString<WebSocketPayload>(message)
            val entity = payload.entity.capitalize()
            val event = payload.event.lowercase()
            val id = payload.data.jsonObject["id"]?.jsonPrimitive?.intOrNull

            val description = when (event) {
                "created" -> "$entity with ID $id has been created."
                "updated" -> "$entity with ID $id has been updated."
                "deleted" -> "$entity with ID $id has been deleted."
                else -> "$entity event occurred."
            }

            showNotification(
                title = "$entity ${event.replaceFirstChar { it.uppercase() }}",
                description = description,
                type = when (event) {
                    "created" -> NotificationType.CREATED
                    "updated" -> NotificationType.UPDATED
                    "deleted" -> NotificationType.DELETED
                    else -> NotificationType.INFO
                }
            )
        } catch (e: Exception) {
            Log.e("WebSocket", "Failed to parse message: $message", e)
        }
    }

}
