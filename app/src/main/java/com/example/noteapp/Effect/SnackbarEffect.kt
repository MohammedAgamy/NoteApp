package com.example.noteapp.Effect

sealed  class SnackbarEffect {    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : SnackbarEffect()


}