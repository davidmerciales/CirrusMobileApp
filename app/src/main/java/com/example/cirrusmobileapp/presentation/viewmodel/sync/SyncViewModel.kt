package com.example.cirrusmobileapp.presentation.viewmodel.sync

import BaseViewModel
import androidx.lifecycle.viewModelScope
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel<SyncContract.State, SyncContract.Intent, SyncContract.Effect>() {

    override fun createInitialState(): SyncContract.State = SyncContract.State()

    override fun handleIntent(intent: SyncContract.Intent) {
        when (intent) {
            is SyncContract.Intent.StartSync -> {
                syncData()
            }

            is SyncContract.Intent.RetrySync -> {
                syncData()
            }

            SyncContract.Intent.CancelSync -> TODO()
        }
    }

    override fun Throwable.toErrorEffect(): SyncContract.Effect {
        TODO("Not yet implemented")
    }

    private fun syncData() {
        setState { copy(isSyncing = true, error = null) }

        viewModelScope.launch {
            try {
                productRepository.refreshProducts()
                setState { copy(isSyncing = false) }
                setEffect(SyncContract.Effect.ShowSuccess)
            } catch (e: Exception) {
                setState { copy(isSyncing = false, error = e.message) }
                setEffect(SyncContract.Effect.ShowError(e.message ?: "Unknown error"))
            }
        }
    }
}