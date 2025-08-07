package com.example.cirrusmobileapp.presentation.viewmodel.sync

import UiEffect
import UiIntent
import UiState

object SyncContract {

    sealed class Intent : UiIntent {
        data object StartSync : Intent()
        data object RetrySync : Intent()
        data object CancelSync : Intent()
    }

    data class State(
        val isSyncing: Boolean = false,
        val progress: Int = 0,
        val syncingMessage: String = "Syncing latest changes...",
        val error: String? = null,
        val successMessage: String? = null
    ) : UiState

    sealed class Effect : UiEffect {
        data class ShowError(val message: String) : Effect()
        data object ShowSuccess : Effect()
    }
}
