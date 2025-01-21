package com.example.noteapp.data.mvi

sealed class UserIntent {
    data class UpdateName(val name: String) : UserIntent()
    data class UpdateEmail(val email: String) : UserIntent()
    data object UndoDelete : UserIntent()
    data class AddUserView(val name: String, val email: String,val phone:String , val password:String) : UserIntent()
    data object LoadUsers : UserIntent()

}