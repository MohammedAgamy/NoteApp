package com.example.noteapp.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.RegisterModel
import com.example.noteapp.repo.reporegister.RepositoryRegister
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: RepositoryRegister) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _nameError = MutableStateFlow(false)
    val nameError: StateFlow<Boolean> = _nameError

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone
    private val _phoneError = MutableStateFlow(false)
    val phoneError: StateFlow<Boolean> = _phoneError

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError


    private val _registerResult = MutableStateFlow<Result<Boolean>?>(null)
    val registerResult: StateFlow<Result<Boolean>?> = _registerResult


    fun viewModelRegister() {
        viewModelScope.launch {
            _registerResult.value = repo.login(
                RegisterModel(
                    _name.value,
                    _phone.value,
                    _email.value,
                    _password.value,
                    nameError = true,
                    phoneError = true,
                    emailError = true,
                    passwordError = true
                )
            )
        }
    }

    fun updateName(input: String) {
        _name.value = input
        _nameError.value =
            input.isBlank()
    }

    fun updatePhone(input: String) {
        _phone.value = input
        _phoneError.value = input.isBlank()  // pass cannot be empty
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