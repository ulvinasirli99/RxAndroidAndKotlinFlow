package nasirli.tool.rxandroidandkotlinflows.di.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize other necessary things here if needed
    }
}