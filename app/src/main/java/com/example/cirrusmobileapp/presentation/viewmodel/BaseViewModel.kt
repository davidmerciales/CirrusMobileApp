import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface UiState

interface UiIntent

abstract class BaseViewModel<T : UiState, I : UiIntent> : ViewModel() {

    // The single, abstract UI state. Subclasses must implement this.
    abstract val uiState: StateFlow<T>

    private val _errorEvents = MutableSharedFlow<String>()
    val errorEvents: SharedFlow<String> = _errorEvents.asSharedFlow()

    abstract fun processIntent(intent: I)

    protected fun safeLaunch(block: suspend () -> Unit) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                _errorEvents.emit(throwable.message ?: "An unknown error occurred.")
            }
        }

        viewModelScope.launch(exceptionHandler) {
            block()
        }
    }
}