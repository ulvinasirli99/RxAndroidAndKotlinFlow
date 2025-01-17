package nasirli.tool.rxandroidandkotlinflows.ui.screens.views.teacher

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher
import nasirli.tool.rxandroidandkotlinflows.domain.models.TeacherDetail
import nasirli.tool.rxandroidandkotlinflows.navigation.routes.Router
import nasirli.tool.rxandroidandkotlinflows.ui.view_models.TeacherListViewModel
import nasirli.tool.rxandroidandkotlinflows.ui.view_models.UiState

@Composable
fun TeacherListScreen(
    viewModel: TeacherListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navCtrl: NavHostController,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getTeachers()
    }

    when (uiState) {
        is UiState.Loading -> Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

        is UiState.Success -> EducatorList(
            (uiState as UiState.Success).educators,
            modifier = modifier,
            navCtrl,
        )

        is UiState.Error -> Text(
            text = "Error: ${(uiState as UiState.Error).message}",
            modifier = modifier
        )
    }
}

@Composable
fun EducatorList(
    educators: List<Teacher>,
    modifier: Modifier = Modifier,
    navCtrl: NavHostController
) {
    LazyColumn(modifier = modifier) {
        items(educators) { educator ->
            EducatorItem(educator, navCtrl)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EducatorItem(teacher: Teacher, navCtrl: NavHostController) {
    Row(modifier = Modifier
        .padding(12.dp)
        .clickable {
            Router(navController = navCtrl).navigateToTeacherDetailScreen(
                TeacherDetail(
                    teacher.image,
                    teacher.subjects,
                    teacher.address.country,
                    teacher.address.zip,
                    teacher.address.city,
                    teacher.address.street,
                    teacher.name,
                )
            )
        }) {
        GlideImage(
            model = teacher.image,
            contentDescription = "Rocket Image",
            modifier = Modifier.size(100.dp)
        )
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = teacher.name)
            Text(text = teacher.email)
        }
    }
}
