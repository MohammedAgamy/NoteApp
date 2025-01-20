package com.example.noteapp.data.mvi

data class UserViewState (
    val isLoading: Boolean = false,
    val users: List<RegisterModel> = emptyList(),
    val name: String = "",
    val email: String = "",
    val nameError: Boolean = false,
    val emailError: Boolean = false,
    val searchQuery: String = "",
    val recentlyDeletedUser: RegisterModel? = null
)