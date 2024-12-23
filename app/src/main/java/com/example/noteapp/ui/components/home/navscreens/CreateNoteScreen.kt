package com.example.noteapp.ui.components.home.navscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
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


@Composable
fun CreateNoteScreen(
    navController: NavHostController
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var content by remember { mutableStateOf(TextFieldValue()) }
    val noteViewModel by lazy { NotesViewModel(NoteRepositoryImpl()) }

    val addId = FirebaseFirestore.getInstance().collection("notes").document()

    //val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth() // Occupy the full width of the screen
                    .padding(16.dp) // Add padding around the Box
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
                    modifier = Modifier
                        .width(160.dp)
                        .align(Alignment.CenterEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black, // Background color of the button
                        contentColor = Color.White    // Text or icon color
                    )
                ) {
                    Text(text = "Save", fontSize = 22.sp)
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
                label = {
                    Text(
                        text = "Note Title ",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                },


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
                label = {
                    Text(
                        text = "Note ...",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                },


                )
        }


    }

}





