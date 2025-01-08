package com.example.noteapp.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.NoteModel
import com.example.noteapp.repo.repoRoom.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModelRoom(private  var repository: NoteRepository) : ViewModel() {

    private val _notes = MutableStateFlow<List<NoteModel>>(emptyList())
    val notes: StateFlow<List<NoteModel>> get() = _notes

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            repository.allNotes.collect {
                _notes.value = it
            }
        }
    }
    fun insert(note: NoteModel) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun update(note: NoteModel) {
        viewModelScope.launch {
            repository.update(note)
        }
    }

    fun delete(note: NoteModel) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }

    fun deleteById(id:Int)
    {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}