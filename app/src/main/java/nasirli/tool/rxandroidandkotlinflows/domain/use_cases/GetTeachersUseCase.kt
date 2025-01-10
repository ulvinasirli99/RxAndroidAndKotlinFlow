package nasirli.tool.rxandroidandkotlinflows.domain.use_cases

import io.reactivex.Single
import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher
import nasirli.tool.rxandroidandkotlinflows.data.repositories.TeacherRepository
import javax.inject.Inject

class GetTeachersUseCase(private val educatorRepository: TeacherRepository) {
    fun getAllTeachers(): Single<List<Teacher>> {
        return educatorRepository.getTeachers()
    }
}