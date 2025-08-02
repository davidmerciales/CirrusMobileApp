package com.example.cirrusmobileapp.data.websocket

import com.example.cirrusmobileapp.domain.websocket.WebSocketEvent
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketListenerImpl(
    private val onEvent: (WebSocketEvent) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        onEvent(WebSocketEvent.OnOpen)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        onEvent(WebSocketEvent.OnMessage(text))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        onEvent(WebSocketEvent.OnFailure(t))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
        onEvent(WebSocketEvent.OnClosed)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        onEvent(WebSocketEvent.OnClosed)
    }
}