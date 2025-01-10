package nasirli.tool.rxandroidandkotlinflows.data.api


import io.reactivex.Single
import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher
import nasirli.tool.rxandroidandkotlinflows.utils.constants.TEACHERS_URL
import retrofit2.http.GET


interface ApiService {
    @GET(TEACHERS_URL)
    fun getTeachers(): Single<List<Teacher>>
}