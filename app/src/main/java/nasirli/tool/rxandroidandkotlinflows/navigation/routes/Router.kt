package nasirli.tool.rxandroidandkotlinflows.navigation.routes

import androidx.navigation.NavController
import com.google.gson.Gson
import nasirli.tool.rxandroidandkotlinflows.domain.models.TeacherDetail
import javax.inject.Inject

class Router @Inject constructor(private val navController: NavController) {

    fun navigateToTeacherListScreen() {
        navController.navigate(CommonScreenToRoute.TeacherList.route)
    }

    fun navigateToTeacherDetailScreen(detailData: TeacherDetail) {
        val json = Gson().toJson(detailData)
        navController.navigate(CommonScreenToRoute.TeacherDetail.getTeacherDetailRoute(json))
    }


    fun navigateBack() {
        navController.popBackStack()
    }
}