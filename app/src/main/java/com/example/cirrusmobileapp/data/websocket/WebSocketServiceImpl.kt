package com.example.cirrusmobileapp.data.websocket

import com.example.cirrusmobileapp.domain.websocket.WebSocketEvent
import com.example.cirrusmobileapp.domain.websocket.WebSocketService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class WebSocketServiceImpl(
    private val url: String
) : WebSocketService {

    private var webSocket: WebSocket? = null

    override fun connect(listener: (WebSocketEvent) -> Unit) {
        val request = Request.Builder().url(url).build()
        val wsListener = WebSocketListenerImpl(listener)
        webSocket = OkHttpClient().newWebSocket(request, wsListener)
    }

    override fun send(message: String) {
        webSocket?.send(message)
    }

    override fun disconnect() {
        webSocket?.close(1000, "Client Closed")
    }
}
