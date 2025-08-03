package com.example.cirrusmobileapp.domain.websocket

sealed class WebSocketEvent {
    object OnOpen : WebSocketEvent()
    data class OnMessage(val message: String) : WebSocketEvent()
    data class OnFailure(val throwable: Throwable) : WebSocketEvent()
    object OnClosed : WebSocketEvent()
}
