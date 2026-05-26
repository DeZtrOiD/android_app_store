package com.example.basicscodelab.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicscodelab.data.model.AppInfo
import com.example.basicscodelab.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AppUiState(
    val isLoading: Boolean = true,
    val apps: List<AppInfo> = emptyList(),
    val errorMessage: String? = null
)

class AppViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            runCatching { repository.getApps() }
                .onSuccess { apps ->
                    _uiState.value = AppUiState(
                        isLoading = false,
                        apps = apps,
                        errorMessage = null
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = AppUiState(
                        isLoading = false,
                        apps = emptyList(),
                        errorMessage = throwable.message ?: "Не удалось загрузить данные"
                    )
                }
        }
    }

    fun appById(id: Int): AppInfo? {
        return uiState.value.apps.firstOrNull { it.id == id }
    }
}