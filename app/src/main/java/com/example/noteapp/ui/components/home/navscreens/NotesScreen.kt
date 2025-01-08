package com.example.noteapp.ui.components.home.navscreens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapp.data.AppDatabase
import com.example.noteapp.data.NoteModel
import com.example.noteapp.models.NoteViewModelRoom
import com.example.noteapp.models.NotesViewModel
import com.example.noteapp.repo.repoRoom.NoteRepository
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    val firestore = FirebaseFirestore.getInstance().collection("notes")

    val context = LocalContext.current

    // Check Internet Connectivity
    val isConnected = remember { isInternetAvailable(context) }

    // Initialize Room Database and Repository
    val database = AppDatabase.getDatabase(context)
    val repository = NoteRepository(database.noteDao())

    // Create Room ViewModel using Factory
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModelRoom::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModelRoom(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    val viewModelRoom: NoteViewModelRoom = viewModel(factory = factory)


    // Observe Firestore and Room Notes
    val notes = viewModel.notesState.collectAsState().value
    val notesRoom = viewModelRoom.notes.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        if (notes.isEmpty() && notesRoom.isEmpty()) {
            Text(
                text = "No notes available. Add a new note!",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn {
                if (isConnected) {
                    items(notes) { note ->
                        NoteListItem(note = note, onDelete = {

                        })
                    }
                } else {
                    items(notesRoom) { note ->
                        NoteListItem(note = note, onDelete = {
                            // Handle Room deletion
                            viewModelRoom.deleteById(note.id)
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun NoteListItem(
    note: NoteModel,
    onDelete: (NoteModel) -> Unit
) {

    val randomColor = remember { generateRandomPastelColor() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = randomColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { /* Add click action if needed */ }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f) // Ensures the column takes available space
        ) {
            Text(
                text = note.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.content,
                fontSize = 14.sp,
                color = Color.White,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Last updated: ${formatTimeStamp(note.timeStamp)}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
        IconButton(
            onClick = { onDelete(note) },
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Note",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

fun formatTimeStamp(timeStamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(timeStamp)
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


// Helper function to generate a random pastel color
fun generateRandomPastelColor(): Color {
    val random = Random()
    val red = (random.nextInt(128) + 128).coerceIn(128, 255) // Ensures a lighter shade
    val green = (random.nextInt(128) + 128).coerceIn(128, 255)
    val blue = (random.nextInt(128) + 128).coerceIn(128, 255)
    return Color(red, green, blue)
}