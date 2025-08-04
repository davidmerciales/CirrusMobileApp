package com.example.cirrusmobileapp.domain.websocket

sealed class StompEvent {
    object OnOpen : StompEvent()
    data class OnMessage(val message: String) : StompEvent()
    data class OnFailure(val throwable: Throwable) : StompEvent()
    object OnClosed : StompEvent()
}