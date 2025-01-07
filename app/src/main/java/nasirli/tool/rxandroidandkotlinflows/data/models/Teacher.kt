package nasirli.tool.rxandroidandkotlinflows.data.models

data class Teacher(
    val id: Int,
    val name: String,
    val age: Int,
    val gender: String,
    val address: Address,
    val email: String,
    val phone: String,
    val subjects: List<String>,
    val yearsOfExperience: Int,
    val image: String
)

data class Address(
    val street: String,
    val city: String,
    val zip: String,
    val country: String
)
