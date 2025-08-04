package com.example.cirrusmobileapp.di

import com.example.cirrusmobileapp.data.websocket.WebSocketServiceImpl
import com.example.cirrusmobileapp.domain.websocket.StompService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebSocketModule {

    @Provides
    @Singleton
    fun provideWebSocketService(): StompService {
//        return WebSocketServiceImpl("ws://10.0.0.120:8080/ws")
        return WebSocketServiceImpl("ws://10.0.0.94:8001/ws")
    }
}