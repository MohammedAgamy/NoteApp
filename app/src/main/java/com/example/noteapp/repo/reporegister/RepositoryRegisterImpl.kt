package com.example.noteapp.repo.reporegister

import com.example.noteapp.data.RegisterModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class RepositoryRegisterImpl : RepositoryRegister {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(registerModel: RegisterModel): Result<Boolean> {

        return try {
            auth.createUserWithEmailAndPassword(registerModel.email, registerModel.password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}