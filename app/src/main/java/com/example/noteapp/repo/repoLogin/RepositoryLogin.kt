package com.example.noteapp.repo.repoLogin


// Repo braidge between model and viewModel and used suspend fun to coroutines
interface RepositoryLogin {
     suspend fun login(email: String, password: String):Result< Boolean>
}

/* suspend fun addData(logInModel: LogInModel):LogInModel
     suspend fun getData():LogInModel*/