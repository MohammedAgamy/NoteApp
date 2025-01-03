package com.example.noteapp.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.repo.repoLogin.RepositoryLogin
import com.example.noteapp.ui.authActivity.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// view model handel operation to do in model date and used state flow
class LoginViewModel(private val repo: RepositoryLogin , private val preferenceManager: PreferenceManager) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError


    private val _loginResult = MutableStateFlow<Result<Boolean>?>(null)
    val loginResult: StateFlow<Result<Boolean>?> = _loginResult


    private val _isLoggedIn = MutableStateFlow(preferenceManager.isLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun logIn() {
        preferenceManager.setLoggedIn(true)
        _isLoggedIn.value = true
    }

    fun logOut() {
        preferenceManager.setLoggedIn(false)
        _isLoggedIn.value = false
    }

    fun viewModelLogIn() {
        viewModelScope.launch {
            _loginResult.value = repo.login(_email.value, _password.value)
        }

    }

    fun updateEmail(input: String) {
        _email.value = input
        _emailError.value =
            input.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(input)
                .matches()  // Check email format
    }

    fun updatePassword(input: String) {
        _password.value = input
        _passwordError.value = input.isBlank()  // pass cannot be empty
    }


}