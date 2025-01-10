package nasirli.tool.rxandroidandkotlinflows.navigation.routes

sealed class CommonScreenToRoute(val route: String) {
    data object TeacherList : CommonScreenToRoute("teacherListScreen")
    data object TeacherDetail : CommonScreenToRoute("teacherDetailScreen?data={data}") {
        fun getTeacherDetailRoute(data: String) = "teacherDetailScreen?data=$data"
    }
}