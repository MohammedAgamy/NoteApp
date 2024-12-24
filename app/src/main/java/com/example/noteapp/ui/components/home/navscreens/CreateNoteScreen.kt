package com.example.noteapp.ui.components.home.navscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.data.NoteModel
import com.example.noteapp.models.LoginViewModel
import com.example.noteapp.models.NotesViewModel
import com.example.noteapp.repo.repoLogin.RepositoryLoginImpl
import com.example.noteapp.repo.reponote.NoteRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    navController: NavHostController
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var content by remember { mutableStateOf(TextFieldValue()) }
    val noteViewModel by lazy { NotesViewModel(NoteRepositoryImpl()) }
    val addId = FirebaseFirestore.getInstance().collection("notes").document()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    noteViewModel.addNote(
                        NoteModel(
                            title = title.text,
                            content = content.text,
                            id = addId.id
                        )
                    )

                    // Navigate back to the "notes" screen and clear the back stack
                    navController.navigate("notes") {
                        popUpTo("notes") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = "Save", color = Color.White, fontSize = 16.sp)
            }
        }


        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            value = title,

            onValueChange = {
                title = it
            },
            placeholder = {
                Text(
                    text = "Page Title",
                    color = Color.Gray,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // No background for the text field
                disabledTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Gray
            )

        )

        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp),
            value = content,

            onValueChange = {
                content = it
            },
            placeholder = {
                Text(text = "Enter your note here...", color = Color.Gray)
            },
            maxLines = Int.MAX_VALUE, // Unlimited lines for long notes
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // No background for the text field
                disabledTextColor = Color.Black,
                cursorColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )
    }


}







