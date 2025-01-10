package nasirli.tool.rxandroidandkotlinflows.domain.models

data class TeacherDetail (
    val imageUrl: String,
    val subjects: List<String>,
    val country: String,
    val zipCode: String,
    val cityName: String,
    val streetName: String,
    val personName: String
)