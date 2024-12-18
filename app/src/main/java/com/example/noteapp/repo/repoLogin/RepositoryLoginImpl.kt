package com.example.noteapp.repo.repoLogin

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await


//what fun in repo to do simulation to user log in and used coroutines
class RepositoryLoginImpl : RepositoryLogin {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }


}

/*    override suspend fun addData(logInModel: LogInModel): LogInModel {
    delay(3000)
    return logInModel
}

override suspend fun getData(): LogInModel {
    delay(3000)
    return LogInModel("jghfjhgghfj", "gg@gmail.com")
}*/
