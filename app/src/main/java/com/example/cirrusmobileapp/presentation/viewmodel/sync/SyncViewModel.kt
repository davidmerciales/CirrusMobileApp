package com.example.cirrusmobileapp.presentation.viewmodel.sync

import BaseViewModel
import androidx.lifecycle.viewModelScope
import com.example.cirrusmobileapp.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SyncViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel<SyncContract.State, SyncContract.Intent, SyncContract.Effect>() {

    override fun createInitialState(): SyncContract.State = SyncContract.State()

    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            productRepository.getProducts().collectLatest { products ->
//                products.forEach { product ->
//                    setState {
//                        copy(productName = product.product.name)
//                    }
//                }
//            }
//        }
    }

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

        viewModelScope.launch(Dispatchers.IO) {
            try {
                delay(1000)
                setState { copy(syncingMessage = "Just a moment, syncing recent updates.") }
                productRepository.refreshProducts()
                withContext(Dispatchers.Main){
                    setState { copy(isSyncing = false, syncingMessage= "Sync complete. Everything’s updated!") }
                    setEffect(SyncContract.Effect.ShowSuccess)
                }
            } catch (e: Exception) {
                setState { copy(isSyncing = false, error = e.message) }
                setEffect(SyncContract.Effect.ShowError(e.message ?: "Unknown error"))
            }
        }
    }
}