package nasirli.tool.rxandroidandkotlinflows.data.repositories

import io.reactivex.Single
import nasirli.tool.rxandroidandkotlinflows.data.api.ApiService
import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher

class TeacherRepositoryImpl(private val apiService: ApiService) : TeacherRepository {
    override fun getTeachers(): Single<List<Teacher>> {
        return apiService.getTeachers()
    }

}
