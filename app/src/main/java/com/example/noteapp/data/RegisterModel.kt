package com.example.noteapp.data

data class RegisterModel(
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val nameError: Boolean = false,
    val phoneError: Boolean = false,
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
)