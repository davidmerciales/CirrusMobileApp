package com.example.cirrusmobileapp.data.websocket

import com.example.cirrusmobileapp.domain.websocket.WebSocketEvent
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketListenerImpl(
    private val listener: (WebSocketEvent) -> Unit
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        listener(WebSocketEvent.OnOpen(response))
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        listener(WebSocketEvent.OnMessage(text))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        listener(WebSocketEvent.OnClosing(code, reason))
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        listener(WebSocketEvent.OnClosed(code, reason))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        listener(WebSocketEvent.OnFailure(t, response))
    }
}
