package com.example.cirrusmobileapp.data.websocket

import android.annotation.SuppressLint
import com.example.cirrusmobileapp.domain.websocket.StompEvent
import com.example.cirrusmobileapp.domain.websocket.StompService
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class WebSocketServiceImpl(
    private val url: String
) : StompService {

    private var stompClient: StompClient? = null

    @SuppressLint("CheckResult")
    override fun connect(listener: (StompEvent) -> Unit) {
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url)
        stompClient?.apply {
            lifecycle().subscribe { lifecycleEvent ->
                when (lifecycleEvent.type) {
                    LifecycleEvent.Type.OPENED -> listener(StompEvent.OnOpen)
                    LifecycleEvent.Type.CLOSED -> listener(StompEvent.OnClosed)
                    LifecycleEvent.Type.ERROR -> listener(StompEvent.OnFailure(lifecycleEvent.exception ?: Exception("Unknown error")))
                    else -> {}
                }
            }

            connect()

//            topic("/topic/getAll").subscribe { topicMessage ->
//                listener(StompEvent.OnMessage(topicMessage.payload))
//            }
            topic("/topic/products").subscribe { topicMessage ->
                listener(StompEvent.OnMessage(topicMessage.payload))
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun subscribe(topic: String, messageHandler: (String) -> Unit) {
        stompClient?.topic(topic)
            ?.subscribe { topicMessage ->
                messageHandler(topicMessage.payload)
            }
    }

    override fun send(destination: String, payload: String) {
        stompClient?.send(destination, payload)
            ?.subscribe()
    }

    override fun disconnect() {
        stompClient?.disconnect()
    }
}
