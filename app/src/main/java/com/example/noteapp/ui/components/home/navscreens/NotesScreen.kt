package com.example.noteapp.ui.components.home.navscreens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noteapp.R
import com.example.noteapp.data.AppDatabase
import com.example.noteapp.data.NoteModel
import com.example.noteapp.models.NoteViewModelRoom
import com.example.noteapp.models.NotesViewModel
import com.example.noteapp.repo.repoRoom.NoteRepository
import com.example.noteapp.repo.reponote.NoteRepositoryImpl
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NotesScreen(viewModel: NotesViewModel) {


    val context = LocalContext.current
    val isConnected = remember { isInternetAvailable(context) }


    // Initialize Room Database and Repository
    val database = AppDatabase.getDatabase(context)
    val repository = NoteRepository(database.noteDao())


    // Create ViewModel using Factory
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModelRoom::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModelRoom(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    val notes = viewModel.notesState.collectAsState().value
    Log.d("fire", notes.toString())

    val viewModelRoom: NoteViewModelRoom = viewModel(factory = factory)

    // Observe and Display Notes
    val notesRoom = viewModelRoom.notes.collectAsState().value
    Log.d("TAGInserrt", notesRoom.toString())

    Box(modifier = Modifier.fillMaxSize()) {
        if (notes.isEmpty() && notesRoom.isEmpty()) {
            Text(
                text = "No notes available. Add a new note!",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            // NoteList(notes = notesRoom.toMutableList())
            if (isInternetAvailable(context)) {
                NoteList(notes = notes.toMutableList())
                Log.d("isConect", isConnected.toString())

            } else {
                NoteList(notes = notesRoom.toMutableList())
                Log.d("isConect", isConnected.toString())

            }


        }
    }
}


@Composable
fun NoteList(notes: MutableList<NoteModel>) {
    val model = NotesViewModel(NoteRepositoryImpl())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {

        items(notes) { note ->
            NoteItem(note = note, onDelete = {
                // model.deleteNote(note)
            })

        }
    }
}

@Composable
fun NoteItem(note: NoteModel, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(3f)

            ) {
                Text(
                    text = note.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = note.content, fontSize = 12.sp, color = Color.Gray)
                Text(
                    text = "Last updated: ${formatTimeStamp(note.timeStamp)}", // Assuming timestamp exists
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 8.dp)
                )

            }
            IconButton(
                onClick =
                onDelete,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red) // Customize the icon color
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_forever_24), // Your custom icon
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error // Optional, use MaterialTheme for consistent coloring
                )
            }
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
