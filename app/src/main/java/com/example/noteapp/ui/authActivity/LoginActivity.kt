package com.example.noteapp.ui.authActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.noteapp.models.LoginViewModel
import com.example.noteapp.repo.repoLogin.RepositoryLoginImpl
import com.example.noteapp.ui.components.login.LogInComponent
import com.example.noteapp.ui.components.login.TextTitle

import com.example.noteapp.ui.theme.NoteAppTheme

class LoginActivity : ComponentActivity() {
    private val loginViewModel by lazy { LoginViewModel(RepositoryLoginImpl()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(Color.White)
                        .padding(top = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    TextTitle()
                    Spacer(modifier = Modifier.height(50.dp))
                    LogInComponent(loginViewModel)
                }

            }
        }
    }
}

