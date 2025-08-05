package com.example.cirrusmobileapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cirrusmobileapp.data.model.Product
import com.example.cirrusmobileapp.data.model.WebSocketPayload
import com.example.cirrusmobileapp.domain.websocket.StompEvent
import com.example.cirrusmobileapp.domain.websocket.StompService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject

enum class NotificationType {
    CREATED, UPDATED, DELETED, INFO
}

data class AppNotification(
    val id: String,
    val title: String,
    val description: String,
    val type: NotificationType = NotificationType.INFO,
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@HiltViewModel
class AppViewModel @Inject constructor(
    private val json: Json,
    private val webSocketService: StompService
) : ViewModel() {
    private val _notificationEvents = MutableSharedFlow<AppNotification>()
    val notificationEvents: SharedFlow<AppNotification> = _notificationEvents.asSharedFlow()

    private val _notifications = MutableStateFlow<List<AppNotification>>(emptyList())
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()

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
        id: String,
        title: String,
        description: String,
        type: NotificationType = NotificationType.INFO
    ) {
        viewModelScope.launch {
            _notificationEvents.emit(
                AppNotification(
                    id = id,
                    title = title,
                    description = description,
                    type = type
                )
            )
        }

        val newNotification = AppNotification(
            id = id,
            title = title,
            description = description,
            type = type
        )

        _notifications.update { currentList ->
            listOf(newNotification) + currentList
        }
    }
    fun markNotificationAsRead(id: String) {
        _notifications.update { currentList ->
            currentList.map {
                if (it.id == id) it.copy(isRead = true) else it
            }
        }
    }

    fun markAllAsRead() {
        _notifications.update { currentList ->
            currentList.map { it.copy(isRead = true) }
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
                id = System.currentTimeMillis().toString(),
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
