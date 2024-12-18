package com.example.noteapp.ui.ui.ui.auth

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
import com.example.noteapp.models.RegisterViewModel
import com.example.noteapp.repo.repoLogin.RepositoryLoginImpl
import com.example.noteapp.repo.reporegister.RepositoryRegisterImpl
import com.example.noteapp.ui.components.login.LogInComponent
import com.example.noteapp.ui.components.login.TextTitle
import com.example.noteapp.ui.components.register.RegisterComponent
import com.example.noteapp.ui.components.register.TextTitleRegister
import com.example.noteapp.ui.ui.ui.theme.NoteAppTheme

class RegistetrActivity : ComponentActivity() {
    private val registerViewModel by lazy { RegisterViewModel(RepositoryRegisterImpl()) }

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
                    TextTitleRegister()
                    Spacer(modifier = Modifier.height(50.dp))
                    RegisterComponent(registerViewModel)
                }
            }
        }
    }
}

