package com.example.cirrusmobileapp.di

import com.example.cirrusmobileapp.data.websocket.WebSocketServiceImpl
import com.example.cirrusmobileapp.domain.websocket.WebSocketService
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
    fun provideWebSocketService(): WebSocketService {
        return WebSocketServiceImpl("ws://10.0.2.2:8080/ws")
    }
}