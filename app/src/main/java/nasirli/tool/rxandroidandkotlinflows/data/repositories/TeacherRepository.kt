package nasirli.tool.rxandroidandkotlinflows.data.repositories

import io.reactivex.Single
import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher

interface TeacherRepository {
    fun getTeachers(): Single<List<Teacher>>
}