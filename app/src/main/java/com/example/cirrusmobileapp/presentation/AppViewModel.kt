package com.example.cirrusmobileapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotificationEvent(val title: String, val message: String)

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {
    private val _notificationEvents = MutableSharedFlow<NotificationEvent>()
    val notificationEvents: SharedFlow<NotificationEvent> = _notificationEvents.asSharedFlow()


    fun showNotification(title: String, message: String) {
        viewModelScope.launch {
            _notificationEvents.emit(NotificationEvent(title, message))
        }
    }
}
