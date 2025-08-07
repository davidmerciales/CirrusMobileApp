package com.example.cirrusmobileapp.presentation.viewmodel.signin

import UiEffect
import UiIntent
import UiState

object SignInContract {

    sealed class Intent : UiIntent {
        data object SignIn : Intent()
        data class EmailChanged(val email: String) : Intent()
        data class PasswordChanged(val password: String) : Intent()
    }

    data class State(
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false,
    ) : UiState

    sealed class Effect : UiEffect {
        data object NavigateToHome : Effect()
        data class ShowError(val message: String) : Effect()
        data object ShowLoginSuccess : Effect()
    }
}