package nasirli.tool.rxandroidandkotlinflows.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import nasirli.tool.rxandroidandkotlinflows.data.api.ApiService
import nasirli.tool.rxandroidandkotlinflows.data.repositories.TeacherRepositoryImpl
import nasirli.tool.rxandroidandkotlinflows.data.repositories.TeacherRepository
import nasirli.tool.rxandroidandkotlinflows.domain.use_cases.GetTeachersUseCase


@Module
@InstallIn(ViewModelComponent::class)
object TeacherModule {
    @Provides
    fun provideTeacherRepository(apiService: ApiService): TeacherRepository {
        return TeacherRepositoryImpl(apiService)
    }

    @Provides
    fun provideGetTeachersUseCase(repository: TeacherRepository): GetTeachersUseCase {
        return GetTeachersUseCase(repository)
    }
}
