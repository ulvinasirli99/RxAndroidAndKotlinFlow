package nasirli.tool.rxandroidandkotlinflows.data.repositories

import nasirli.tool.rxandroidandkotlinflows.data.models.Teacher

interface TeacherRepository {
    suspend fun getTeachers(): List<Teacher>
}