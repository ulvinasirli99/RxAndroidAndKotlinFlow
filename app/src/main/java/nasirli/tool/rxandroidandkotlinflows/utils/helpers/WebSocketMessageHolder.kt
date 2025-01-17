package nasirli.tool.rxandroidandkotlinflows.utils.helpers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WebSocketMessageHolder {
    private val _messages = MutableStateFlow<String?>(null)
    val messages: StateFlow<String?> = _messages

    fun postMessage(message: String) {
        _messages.value = message
    }
}
