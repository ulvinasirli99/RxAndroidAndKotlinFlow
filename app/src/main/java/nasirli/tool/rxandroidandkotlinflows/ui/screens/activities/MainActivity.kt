package nasirli.tool.rxandroidandkotlinflows.ui.screens.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import nasirli.tool.rxandroidandkotlinflows.navigation.controllers.NavGraph
import nasirli.tool.rxandroidandkotlinflows.ui.screens.views.TeacherListScreen
import nasirli.tool.rxandroidandkotlinflows.ui.theme.RxAndroidAndKotlinFlowsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RxAndroidAndKotlinFlowsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph.AppNavGraph(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}