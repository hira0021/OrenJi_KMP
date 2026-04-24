package com.example.orenji.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.orenji.data.repository.AuthRepository
import com.example.orenji.domain.model.User
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
)

class AuthViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _state.value = _state.value.copy(error = "Email and password required")
            return
        }

        viewModelScope.launch {
            _state.value = AuthState(isLoading = true)
            authRepository.login(email, password)
                .onSuccess { user ->
                    _state.value = AuthState(isLoggedIn = true, user = user)
                }
                .onFailure { e ->
                    _state.value = AuthState(
                        error = when {
                            e.message?.contains("401") == true -> "Invalid email or password"
                            e.message?.contains("Unable to resolve") == true -> "Cannot reach server"
                            else -> "Something went wrong"
                        }
                    )
                }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}