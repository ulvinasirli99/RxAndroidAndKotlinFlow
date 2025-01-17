package nasirli.tool.rxandroidandkotlinflows.navigation.controllers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import nasirli.tool.rxandroidandkotlinflows.domain.models.TeacherDetail
import nasirli.tool.rxandroidandkotlinflows.navigation.routes.CommonScreenToRoute
import nasirli.tool.rxandroidandkotlinflows.ui.screens.views.teacher.TeacherDetailScreen
import nasirli.tool.rxandroidandkotlinflows.ui.screens.views.teacher.TeacherListScreen

object NavGraph {
    @Composable
    fun AppNavGraph(
        startDestination: String = CommonScreenToRoute.TeacherList.route,
        modifier: Modifier
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = startDestination) {
            composable(CommonScreenToRoute.TeacherList.route) {
                TeacherListScreen(
                    navCtrl = navController,
                    modifier = modifier
                )
            }
            composable(route = CommonScreenToRoute.TeacherDetail.route, arguments = listOf(
                navArgument("data") { type = NavType.StringType }
            )) { backStackEntry ->
                val json = backStackEntry.arguments?.getString("data")
                val detailTeacherData = Gson().fromJson(json, TeacherDetail::class.java)
                TeacherDetailScreen(detailTeacherData)
            }
        }
    }
}