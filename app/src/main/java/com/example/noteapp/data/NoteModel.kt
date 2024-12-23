package com.example.noteapp.data

data class NoteModel(
    val id: String = "",
    val title: String = "",
    val content: String = "" ,
    var timeStamp: Long = System.currentTimeMillis()
)