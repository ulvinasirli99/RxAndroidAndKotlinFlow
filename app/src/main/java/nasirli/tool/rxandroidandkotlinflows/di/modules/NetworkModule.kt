package nasirli.tool.rxandroidandkotlinflows.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nasirli.tool.rxandroidandkotlinflows.data.api.ApiService
import nasirli.tool.rxandroidandkotlinflows.utils.constants.BASE_URL
import nasirli.tool.rxandroidandkotlinflows.utils.helpers.AppEnvironment
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(context: Context): Retrofit {
        val envMap = AppEnvironment().loadEnv(context)
        val teacherBaseUrl = envMap["TEACHER_BASE_URL"]
        return Retrofit.Builder()
            .baseUrl(teacherBaseUrl.toString())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}