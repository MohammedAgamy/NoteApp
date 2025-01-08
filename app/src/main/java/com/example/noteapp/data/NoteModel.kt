package com.example.noteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
 data class NoteModel(
    @PrimaryKey( autoGenerate = true) var id: Int = 0,
    var title: String = "",
    var content: String = "" ,
    var timeStamp: Long = System.currentTimeMillis()
)