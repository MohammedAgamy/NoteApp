package com.example.noteapp.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.NoteModel
import com.example.noteapp.repo.reponote.NoteRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepositoryImpl) : ViewModel() {
    private val _notesState = MutableStateFlow<List<NoteModel>>(emptyList())
    val notesState: StateFlow<List<NoteModel>> get() = _notesState


    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        viewModelScope.launch {
            try {
                val notes = repository.getNotes() // Fetch notes from repository
                _notesState.value = notes
                Log.e("FetchNotes", "Done fetching notes:")
            } catch (e: Exception) {
                Log.e("FetchNotes", "Error fetching notes: ${e.message}")
            }
        }
    }

    fun addNote(note: NoteModel) {
        viewModelScope.launch {
            repository.addNote(note)
            fetchNotes() // Refresh the state
        }
    }

    fun deleteNote(noteId: NoteModel) {
        viewModelScope.launch {
            repository.deleteNote(noteId)

            fetchNotes() // Refresh the state
        }
    }

}