package nasirli.tool.rxandroidandkotlinflows.domain.use_cases

import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher
import nasirli.tool.rxandroidandkotlinflows.data.repositories.TeacherRepository

class GetTeachersUseCase(private val educatorRepository: TeacherRepository) {
    suspend fun getAllTeachers(): List<Teacher> {
        return educatorRepository.getTeachers()
    }
}