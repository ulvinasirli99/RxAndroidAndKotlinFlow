package nasirli.tool.rxandroidandkotlinflows.di.modules

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nasirli.tool.rxandroidandkotlinflows.utils.helpers.AppEnvironment
import nasirli.tool.rxandroidandkotlinflows.utils.helpers.CustomWebSocketListener
import nasirli.tool.rxandroidandkotlinflows.utils.helpers.WebSocketMessageHolder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WebSocketModule {

    private const val TAG = "WebSocketModule"

    @Provides
    @Singleton
    fun provideWebSocketMessageHolder(): WebSocketMessageHolder = WebSocketMessageHolder()

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideCustomWebSocketListener(messageHolder: WebSocketMessageHolder): CustomWebSocketListener {
        return CustomWebSocketListener(
            messageHolder = messageHolder,
            onError = { error ->
                Log.d(TAG, "WebSocket error: ${error.message}")
            }
        )
    }

    @Provides
    fun provideWebSocket(
        client: OkHttpClient,
        customWebSocketListener: CustomWebSocketListener,
        context: Context,
    ): WebSocket {
        val envMap = AppEnvironment().loadEnv(context)
        val tradeSecretKey = envMap["TRADE_SECRET_KEY"]
        val tradeSocketUrl = envMap["TRADE_SOCKET_URL"]
        val request = Request.Builder()
            .url("$tradeSocketUrl$tradeSecretKey")
            .build()


        return client.newWebSocket(request, customWebSocketListener)
    }

}
