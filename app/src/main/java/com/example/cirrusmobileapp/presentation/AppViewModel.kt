package com.example.cirrusmobileapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cirrusmobileapp.data.model.Product
import com.example.cirrusmobileapp.data.model.WebSocketPayload
import com.example.cirrusmobileapp.domain.websocket.StompEvent
import com.example.cirrusmobileapp.domain.websocket.StompService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
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

@HiltViewModel
class AppViewModel @Inject constructor(
    private val json: Json,
    private val webSocketService: StompService
) : ViewModel() {
    private val _notificationEvents = MutableSharedFlow<AppNotification>()
    val notificationEvents: SharedFlow<AppNotification> = _notificationEvents.asSharedFlow()

    init {
        webSocketService.connect { event ->
            when (event) {
                is StompEvent.OnOpen -> Log.d("WebSocket", "Connected!")
                is StompEvent.OnMessage -> {
                    Log.d("WebSocket", "Received: ${event.message}")
                    handleWebSocketPayload(event.message)
                }
                is StompEvent.OnFailure -> Log.e("WebSocket", "Error: ${event.throwable}")
                is StompEvent.OnClosed -> Log.d("WebSocket", "Closed")
                else -> {
                    Log.d("WebSocket", "Default event")
                }
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

    override fun onCleared() {
        super.onCleared()
        webSocketService.disconnect()
    }

    fun handleWebSocketPayload(message: String) {
        val trimmed = message.trim()
        if (!trimmed.startsWith("{")) {
            Log.w("WebSocket", "Received non-JSON message: $message")
            return
        }

        try {
            val payload = json.decodeFromString<WebSocketPayload>(trimmed)
            val entity = payload.type.capitalize()
            val event = payload.action.lowercase()
            val product = json.decodeFromJsonElement<Product>(payload.data)

            val description = when (event) {
                "created" -> "$entity with ID ${product.id} has been created."
                "updated" -> "$entity with ID ${product.id} has been updated."
                "deleted" -> "$entity with ID ${product.id} has been deleted."
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
