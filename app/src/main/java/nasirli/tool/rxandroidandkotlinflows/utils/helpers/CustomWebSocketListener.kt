package nasirli.tool.rxandroidandkotlinflows.utils.helpers

import nasirli.tool.rxandroidandkotlinflows.ui.view_models.TradeViewModel
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class CustomWebSocketListener(
    private val messageHolder: WebSocketMessageHolder,
    private val onError: (Throwable) -> Unit,
) : WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
        println("WebSocket connected: ${response.message}")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("Received message Final: $text")
        messageHolder.postMessage(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("Received binary message: ${bytes.hex()}")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        println("WebSocket is closing: Code=$code, Reason=$reason")
        webSocket.close(code, reason)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        println("WebSocket closed: Code=$code, Reason=$reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
        println("WebSocket error: ${t.message}")
        onError(t)
    }
}