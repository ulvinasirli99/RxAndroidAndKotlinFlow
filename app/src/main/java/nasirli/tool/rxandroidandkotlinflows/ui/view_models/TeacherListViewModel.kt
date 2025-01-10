package nasirli.tool.rxandroidandkotlinflows.ui.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nasirli.tool.rxandroidandkotlinflows.domain.models.Teacher
import nasirli.tool.rxandroidandkotlinflows.domain.use_cases.GetTeachersUseCase
import javax.inject.Inject

@HiltViewModel
class TeacherListViewModel @Inject constructor(
    private val getTeachersUseCase: GetTeachersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState

    private val compositeDisposable = CompositeDisposable()

    fun getTeachers() {

        _uiState.value = UiState.Loading

        // Using RxJava to get data from UseCase
        val disposable = getTeachersUseCase.getAllTeachers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { teachers ->
                    _uiState.value = UiState.Success(teachers)
                },
                { throwable ->
                    _uiState.value = UiState.Error(throwable.message ?: "Unknown Error")
                }
            )

        // Add the disposable to be cleared when the ViewModel is destroyed
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}


sealed class UiState {
    data object Loading : UiState()
    data class Success(val educators: List<Teacher>) : UiState()
    data class Error(val message: String) : UiState()
}
