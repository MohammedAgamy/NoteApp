package com.example.noteapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.noteapp.models.LoginViewModel
import com.example.noteapp.repo.repoLogin.RepositoryLogin
import com.example.noteapp.repo.repoLogin.RepositoryLoginImpl
import com.example.noteapp.ui.components.pager.NavPager
import com.example.noteapp.ui.theme.NoteAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val loginViewModel by lazy { LoginViewModel(RepositoryLoginImpl()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        // splash screen
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(2000)
            keepSplashScreen = false
        }

        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                //view navPager to start for app
                val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
                if (isLoggedIn) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    NavPager()
                }

            }
        }
    }
}

