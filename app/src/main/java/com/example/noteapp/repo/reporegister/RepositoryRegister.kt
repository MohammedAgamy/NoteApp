package com.example.noteapp.repo.reporegister

import com.example.noteapp.data.mvi.RegisterModel

interface RepositoryRegister {
    suspend fun login(registerModel: RegisterModel): Result<Boolean>
}