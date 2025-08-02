package com.example.cirrusmobileapp.domain.websocket

interface WebSocketService {
    fun connect(listener: (WebSocketEvent) -> Unit)
    fun send(message: String)
    fun disconnect()
}