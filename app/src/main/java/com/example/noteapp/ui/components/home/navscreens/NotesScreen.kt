package com.example.noteapp.ui.components.home.navscreens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.noteapp.R
import com.example.noteapp.data.NoteModel
import com.example.noteapp.models.NotesViewModel
import com.example.noteapp.repo.reponote.NoteRepositoryImpl
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    val notes = viewModel.notesState.collectAsState().value
    Box(modifier = Modifier.fillMaxSize()) {
        if (notes.isEmpty()) {
            Text(
                text = "No notes available. Add a new note!",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {

            NoteList(notes = notes.toMutableList())
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
                model.deleteNote(note)
            })

        }
    }
}

@Composable
fun NoteItem(note: NoteModel, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 4.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = note.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = note.content)
                Text(
                    text = "Last updated: ${formatTimeStamp(note.timeStamp)}", // Assuming timestamp exists
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 8.dp)
                )

                IconButton(
                    onClick =
                    onDelete,
                    modifier = Modifier.padding(16.dp),
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
}

fun formatTimeStamp(timeStamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return sdf.format(timeStamp)
}
