package nasirli.tool.rxandroidandkotlinflows.ui.screens.views.teacher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import nasirli.tool.rxandroidandkotlinflows.domain.models.TeacherDetail
import nasirli.tool.rxandroidandkotlinflows.navigation.routes.Router

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TeacherDetailScreen(detailData: TeacherDetail, navHostController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(12.dp)
        ) {

            // Display the Image
            Box(modifier = Modifier.fillMaxWidth()) {
                GlideImage(
                    model = detailData.imageUrl,
                    contentDescription = "Rocket Image",
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Person Name
            Text(
                text = detailData.personName,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display Address
            Text(
                text = "Address:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text("${detailData.streetName}, ${detailData.cityName}, ${detailData.zipCode}, ${detailData.country}")

            Spacer(modifier = Modifier.height(16.dp))

            // Display Subjects
            Text(
                text = "Subjects:",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            detailData.subjects.forEach { subject ->
                Text("- $subject", style = MaterialTheme.typography.labelMedium)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    Router(navHostController).navigateToTradeWebSocketScreen()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Navigate To Trade Screen.",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = Color.White,
                    ),
                )
            }
        }
    }
}
