package com.example.cirrusmobileapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cirrusmobileapp.data.websocket.WebSocketServiceImpl
import com.example.cirrusmobileapp.domain.websocket.WebSocketEvent
import com.example.cirrusmobileapp.domain.websocket.WebSocketService
import com.example.cirrusmobileapp.ui.theme.CirrusMobileAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var webSocketService: WebSocketService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CirrusMobileAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // Initialize and connect
        webSocketService = WebSocketServiceImpl("ws://10.0.2.2:8080/ws/chat")
        webSocketService.connect { event ->
            when (event) {
                is WebSocketEvent.OnOpen -> Log.d("WebSocket", "Connected!")
                is WebSocketEvent.OnMessage -> Log.d("WebSocket", "Received: ${event.message}")
                is WebSocketEvent.OnFailure -> Log.e("WebSocket", "Error: ${event.throwable}")
                is WebSocketEvent.OnClosed -> Log.d("WebSocket", "Closed")
            }
        }

        // Delay then send a test message
        Handler(Looper.getMainLooper()).postDelayed({
            webSocketService.send("Hello from MainActivity")
        }, 2000)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CirrusMobileAppTheme {
        Greeting("Android")
    }
}