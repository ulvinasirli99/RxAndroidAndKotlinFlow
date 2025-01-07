package nasirli.tool.rxandroidandkotlinflows.ui.view_models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher
import nasirli.tool.rxandroidandkotlinflows.domain.use_cases.GetTeachersUseCase
import javax.inject.Inject

@HiltViewModel
class TeacherListViewModel @Inject constructor(
    private val getEducatorsUseCase: GetTeachersUseCase
) : ViewModel() {
    private val TAG = "TeacherListViewModel"
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun getTeachers() {
        viewModelScope.launch {

            _uiState.value = UiState.Loading

            try {

                val educators = getEducatorsUseCase.getAllTeachers()
                Log.d(TAG, "getTeachers: All Teachers Data : $educators")
                _uiState.value = UiState.Success(educators)

            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}

sealed class UiState {
    data object Loading : UiState()
    data class Success(val educators: List<Teacher>) : UiState()
    data class Error(val message: String) : UiState()
}
