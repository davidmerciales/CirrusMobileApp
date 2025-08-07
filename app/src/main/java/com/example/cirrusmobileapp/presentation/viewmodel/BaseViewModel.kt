import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface UiState
interface UiIntent
interface UiEffect

abstract class BaseViewModel<State : UiState, Intent : UiIntent, Effect : UiEffect> : ViewModel() {

    protected abstract fun createInitialState(): State

    protected abstract fun handleIntent(intent: Intent)

    private val _uiState = MutableStateFlow(createInitialState())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    val effect: SharedFlow<Effect> = _effect.asSharedFlow()

    protected val currentState: State
        get() = uiState.value

    protected fun setState(reducer: State.() -> State) {
        _uiState.value = currentState.reducer()
    }

    protected fun setEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    fun processIntent(intent: Intent) {
        handleIntent(intent)
    }

    protected fun safeLaunch(
        onError: (Throwable) -> Unit = { setEffect(it.toErrorEffect()) },
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (throwable: Throwable) {
                onError(throwable)
            }
        }
    }

    protected abstract fun Throwable.toErrorEffect(): Effect

    protected fun onStateChanged(state: State) {
    }

    protected fun setEffects(vararg effects: Effect) {
        effects.forEach { setEffect(it) }
    }
}