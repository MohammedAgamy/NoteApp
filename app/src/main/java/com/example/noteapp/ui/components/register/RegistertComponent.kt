package com.example.noteapp.ui.components.register

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.noteapp.models.RegisterViewModel
import com.example.noteapp.ui.HomeActivity
import com.example.noteapp.ui.authActivity.LoginActivity

@Composable
fun RegisterComponent(
    registerViewModel: RegisterViewModel
) {

    val context = LocalContext.current
    /*
       patter to validation in email is correct or not
       val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")*/
    val name by registerViewModel.name.collectAsState()
    val nameError by registerViewModel.nameError.collectAsState()

    val phone by registerViewModel.phone.collectAsState()
    val phoneError by registerViewModel.phoneError.collectAsState()

    val email by registerViewModel.email.collectAsState()
    val emailError by registerViewModel.emailError.collectAsState()


    val password by registerViewModel.password.collectAsState()
    val passwordError by registerViewModel.passwordError.collectAsState()

    val registerResult by registerViewModel.registerResult.collectAsState()


    OutlinedTextField(
        value = name,
        onValueChange = {
            registerViewModel.updateName(it)
        },
        label = { Text("Enter Your Name ") },
        isError = nameError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 30.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
    )

    OutlinedTextField(
        value = phone,
        onValueChange = {
            registerViewModel.updatePhone(it)
        },
        label = { Text("Enter Your Phone ") },
        isError = phoneError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .padding(horizontal = 30.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Phone
        ),
    )
    OutlinedTextField(
        value = email,
        onValueChange = {
            registerViewModel.updateEmail(it)
        },
        label = { Text("Enter Your Email ") },
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
            registerViewModel.updatePassword(it)
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
            registerViewModel.addUser(
                name = name,
                email = email,
                phone = phone,
                password = password
            )
            Log.d("TAG", email)
            Log.d("TAG", password)


        }, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(20.dp)

    ) {
        Text(text = "Register", color = Color.White, fontSize = 20.sp)
    }

    registerResult?.let { result ->
        result.fold(
            onSuccess = {
                Toast.makeText(context, "Register Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            },
            onFailure = {
                Toast.makeText(context, "Register Failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
        )
    }


    Spacer(modifier = Modifier.height(80.dp))
    Text(text = "-OR-", fontSize = 20.sp, color = Color.Gray)
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "LogIn",
        fontWeight = FontWeight.Thin,
        fontSize = 35.sp,
        modifier = Modifier
            .clickable {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }

    )

}


