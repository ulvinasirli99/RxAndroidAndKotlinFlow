package nasirli.tool.rxandroidandkotlinflows.di.modules

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nasirli.tool.rxandroidandkotlinflows.ui.view_models.TradeViewModel
import nasirli.tool.rxandroidandkotlinflows.utils.constants.TRADE_SECRET_KEY
import nasirli.tool.rxandroidandkotlinflows.utils.constants.TRADE_WEB_SOCKET
import nasirli.tool.rxandroidandkotlinflows.utils.helpers.CustomWebSocketListener
import nasirli.tool.rxandroidandkotlinflows.utils.helpers.WebSocketMessageHolder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WebSocketModule {

    @Provides
    @Singleton
    fun provideWebSocketMessageHolder(): WebSocketMessageHolder = WebSocketMessageHolder()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideCustomWebSocketListener(messageHolder: WebSocketMessageHolder): CustomWebSocketListener {
        return CustomWebSocketListener(
            messageHolder = messageHolder,
            onError = { error ->
                println(
                    "WebSocket error: ${error.message}"
                )
            }
        )
    }

    @Provides
    @Singleton
    fun provideWebSocket(
        client: OkHttpClient,
        customWebSocketListener: CustomWebSocketListener
    ): WebSocket {
        val request = Request.Builder()
            .url("$TRADE_WEB_SOCKET$TRADE_SECRET_KEY")
            .build()


        return client.newWebSocket(request, customWebSocketListener)
    }

}
