package com.example.cirrusmobileapp.domain.websocket

import okhttp3.Response

sealed class WebSocketEvent {
    data class OnMessage(val message: String) : WebSocketEvent()
    data class OnOpen(val response: Response) : WebSocketEvent()
    data class OnFailure(val throwable: Throwable, val response: Response?) : WebSocketEvent()
    data class OnClosing(val code: Int, val reason: String) : WebSocketEvent()
    data class OnClosed(val code: Int, val reason: String) : WebSocketEvent()
}
