package com.example.noteapp.ui.components.home.navscreens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.noteapp.data.AppDatabase
import com.example.noteapp.data.NoteModel
import com.example.noteapp.models.NoteViewModelRoom
import com.example.noteapp.models.NotesViewModel
import com.example.noteapp.repo.repoRoom.NoteRepository
import com.example.noteapp.repo.reponote.NoteRepositoryImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(navController: NavHostController) {

    var title by remember { mutableStateOf(TextFieldValue()) }
    var content by remember { mutableStateOf(TextFieldValue()) }

    // Context and ViewModel setup
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = NoteRepository(database.noteDao())
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModelRoom::class.java)) {
                @Suppress("UNCHECKED_CAST") return NoteViewModelRoom(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    val noteViewModelRoom: NoteViewModelRoom = viewModel(factory = factory)
    val noteViewModel by lazy { NotesViewModel(NoteRepositoryImpl()) }








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
                    // Validate input
                    if (title.text.isNotBlank() && content.text.isNotBlank()) {
                        noteViewModelRoom.insert(
                            NoteModel(
                                title = title.text,
                                content = content.text, timeStamp = generateTimestampId()
                            )
                        )

                        noteViewModel.addNote(
                            NoteModel(
                                title = title.text,
                                content = content.text,


                                )
                        )
                        // Navigate back to "notes"
                        navController.navigate("notes") {
                            popUpTo("notes") { inclusive = true }
                        }
                    } else {
                        Log.d("CreateNoteScreen", "Title or Content cannot be empty")
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
            onValueChange = { title = it },
            placeholder = {
                Text(
                    text = "Page Title",
                    color = Color.Gray,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
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
            onValueChange = { content = it },
            placeholder = {
                Text(text = "Enter your note here...", color = Color.Gray)
            },
            maxLines = Int.MAX_VALUE,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}



fun generateTimestampId(): Long {
    return System.currentTimeMillis()
}
