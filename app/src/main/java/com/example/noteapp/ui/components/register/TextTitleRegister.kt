package com.example.noteapp.ui.components.register

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TextTitleRegister() {
    Text(
        text = "Register",
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        color = Color(0xFF007AFF),
        textAlign = TextAlign.Center
    )
}