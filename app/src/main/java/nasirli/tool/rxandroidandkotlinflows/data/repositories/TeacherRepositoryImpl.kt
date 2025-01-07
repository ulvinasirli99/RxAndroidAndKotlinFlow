package nasirli.tool.rxandroidandkotlinflows.data.repositories

import nasirli.tool.rxandroidandkotlinflows.data.api.ApiService
import nasirli.tool.rxandroidandkotlinflows.data.models.Teacher

class TeacherRepositoryImpl(private val apiService: ApiService) : TeacherRepository {
    override suspend fun getTeachers(): List<Teacher> {
        return apiService.getTeachers()
    }
}
