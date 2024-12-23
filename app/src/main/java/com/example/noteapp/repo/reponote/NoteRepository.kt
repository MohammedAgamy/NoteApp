package com.example.noteapp.repo.reponote

import com.example.noteapp.data.NoteModel

interface NoteRepository {
    suspend fun getNotes(): List<NoteModel>
    suspend fun addNote(noteModel: NoteModel)
    suspend fun deleteNote(noteId: NoteModel):List<NoteModel>
}