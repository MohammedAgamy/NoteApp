package com.example.noteapp.ui.components.login

import android.content.Intent
import android.content.res.Resources.Theme
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.models.LoginViewModel
import com.example.noteapp.ui.HomeActivity
import com.example.noteapp.ui.authActivity.RegistetrActivity


@Composable
fun LogInComponent(
    viewModel: LoginViewModel
) {
    val context = LocalContext.current
    /*
       patter to validation in email is correct or not
       val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")*/


    val email by viewModel.email.collectAsState()
    val emailError by viewModel.emailError.collectAsState()


    val password by viewModel.password.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    val loginResult by viewModel.loginResult.collectAsState()

    OutlinedTextField(
        value = email,
        onValueChange = {
            viewModel.updateEmail(it)
        },
        label = { Text(text = "Enter Your Email ") },
        isError = emailError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 30.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),


        )

    OutlinedTextField(
        value = password,
        onValueChange = {
            viewModel.updatePassword(it)
        },
        label = { Text("Enter Your Password ") },
        isError = passwordError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 30.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),


        )


    Button(
        onClick = {
            viewModel.viewModelLogIn()
            Log.d("TAG", email)
            Log.d("TAG", password)
            viewModel.setLoginState(true)
            Log.d("TAG", viewModel.isLoggedIn.toString())

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(20.dp),


        ) {
        Text(text = "LogIn", color = Color.White, fontSize = 20.sp)
    }

    loginResult?.let { result ->
        result.fold(
            onSuccess = {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            },
            onFailure = {
                Toast.makeText(context, "Login Failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
        )
    }


    Spacer(modifier = Modifier.height(80.dp))
    Text(text = "-OR-", fontSize = 20.sp, color = Color.Gray)
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Register",
        fontWeight = FontWeight.Thin,
        fontSize = 35.sp,
        modifier = Modifier
            .clickable {
                val intent = Intent(context, RegistetrActivity::class.java)
                context.startActivity(intent)
            }

    )

}

