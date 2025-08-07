package com.example.cirrusmobileapp.presentation.viewmodel.signin

import BaseViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
) : BaseViewModel<SignInContract.State, SignInContract.Intent, SignInContract.Effect>() {

    override fun createInitialState() = SignInContract.State()

    init {
    }

    override fun handleIntent(intent: SignInContract.Intent) {
        when (intent) {
            is SignInContract.Intent.SignIn -> {
                signIn()
            }
            is SignInContract.Intent.EmailChanged -> {
                updateEmail(intent.email)
            }
            is SignInContract.Intent.PasswordChanged -> {
                updatePassword(intent.password)
            }
        }
    }

    fun updateEmail(email: String) {
       setState {
           copy(
               email = email.trim()
           )
       }
    }

    fun updatePassword(password: String) {
        setState {
            copy(
                password = password.trim()
            )
        }
    }

    fun signIn() = viewModelScope.launch {
        safeLaunch {
            setState { copy(isLoading = true) }

            delay(2000)

            if (currentState.email == "admin" && currentState.password == "admin"){
                setState { copy(isLoading = false) }
                setEffect(SignInContract.Effect.NavigateToSync)
            } else {
                setState { copy(isLoading = false) }
                setEffect(SignInContract.Effect.ShowError("Invalid email or password"))
            }

        }
    }

    override fun Throwable.toErrorEffect(): SignInContract.Effect {
        TODO("Not yet implemented")
    }
}