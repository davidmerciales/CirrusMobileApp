package com.example.cirrusmobileapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class LoadingState {
    LOADING, IDLE
}

// Using a sealed interface allows inheritors to be in different packages.
interface UiState

// Using a sealed interface allows inheritors to be in different packages.
interface UiIntent

abstract class BaseViewModel<T : UiState, I : UiIntent>(initialState: T) : ViewModel() {

    // The core UI state for the ViewModel, exposed as an immutable StateFlow.
    protected val _uiState: MutableStateFlow<T> = MutableStateFlow(initialState)
    val uiState: StateFlow<T> = _uiState.asStateFlow()

    private val _loadingState = MutableStateFlow(LoadingState.IDLE)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    abstract fun processIntent(intent: I)

    protected fun safeLaunch(block: suspend () -> Unit) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                _errorEvents.emit(throwable.message ?: "An unknown error occurred.")
                _loadingState.value = LoadingState.IDLE
            }
        }

        viewModelScope.launch(exceptionHandler) {
            _loadingState.value = LoadingState.LOADING
            block()
            _loadingState.value = LoadingState.IDLE
        }
    }
}
