package com.example.cirrusmobileapp.domain.websocket

interface StompService {
    fun connect(listener: (StompEvent) -> Unit)
    fun subscribe(topic: String, messageHandler: (String) -> Unit)
    fun send(destination: String, payload: String)
    fun disconnect()
}