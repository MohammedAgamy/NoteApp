package com.example.noteapp.repo.reponote

import android.util.Log
import com.example.noteapp.data.NoteModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NoteRepositoryImpl : NoteRepository {
    val note = mutableListOf<NoteModel>()

    private val firestore = FirebaseFirestore.getInstance()
    private val notesCollection = firestore.collection("notes")
    override suspend fun getNotes(): List<NoteModel> {
        return try {
            val snapshot = notesCollection.get().await()
            snapshot.documents.map { doc ->
                doc.toObject(NoteModel::class.java)!!.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addNote(noteModel: NoteModel) {
        notesCollection.add(noteModel).await()
    }

    override suspend fun deleteNote(noteId: NoteModel) :List<NoteModel>{
        notesCollection.document(noteId.id).delete().await()
        note.remove(noteId)
        return note
    }

}