package nasirli.tool.rxandroidandkotlinflows.utils.helpers

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

class AppEnvironment {
    // Function to load the env file from the assets folder
    fun loadEnv(context: Context): Map<String, String> {
        val envMap = mutableMapOf<String, String>()
        try {
            // Open the env file from the assets folder
            val inputStream = context.assets.open("env")
            val reader = BufferedReader(InputStreamReader(inputStream))

            reader.forEachLine { line ->
                // Parse only valid lines containing key-value pairs
                if (line.contains("=") && !line.startsWith("#")) {
                    val (key, value) = line.split("=", limit = 2)
                    envMap[key.trim()] = value.trim()
                }
            }
            reader.close()

            Log.d("ENV", "env file loaded successfully")
        } catch (e: Exception) {
            Log.e("ENV", "Error loading env file: ${e.message}")
        }
        return envMap
    }
}