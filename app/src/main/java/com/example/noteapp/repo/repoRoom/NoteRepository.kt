package com.example.noteapp.repo.repoRoom

import com.example.noteapp.data.NoteModel
import com.example.noteapp.repo.NoteDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<NoteModel>> = noteDao.getAllNotes()


    suspend fun insert(note: NoteModel) {
        noteDao.insert(note)
    }

    suspend fun update(note: NoteModel) {
        noteDao.update(note)
    }

    suspend fun delete(note: NoteModel) {
        noteDao.delete(note)
    }

    suspend fun deleteById(id:Int)
    {
        noteDao.deleteById(id)
    }
}