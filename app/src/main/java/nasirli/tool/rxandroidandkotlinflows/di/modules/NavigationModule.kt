package nasirli.tool.rxandroidandkotlinflows.di.modules

import androidx.navigation.NavController
import androidx.navigation.NavHostController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    @Provides
    @Singleton
    fun providerNavController():NavController = NavHostController(ContextAmbient)
}