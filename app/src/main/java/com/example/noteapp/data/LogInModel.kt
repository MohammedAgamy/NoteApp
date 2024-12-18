package com.example.noteapp.data
// class model to logIn email and pass .... we will used (mvvm)
data class LogInModel (

    val email:String  = "",
    val password:String = "" ,
    val passwordError:Boolean = false,
    val emailError:Boolean = false

)