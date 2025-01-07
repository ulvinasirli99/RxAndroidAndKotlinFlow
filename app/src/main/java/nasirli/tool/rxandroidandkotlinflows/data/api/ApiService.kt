package nasirli.tool.rxandroidandkotlinflows.data.api

import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher
import nasirli.tool.rxandroidandkotlinflows.utils.constants.TEACHERS_URL
import retrofit2.http.GET

interface ApiService {
    @GET(TEACHERS_URL)
    suspend fun getTeachers(): List<Teacher>
}