package com.example.noteapp.ui.components.home.navscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun SearchScreen() {

    val context = LocalContext.current


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


    val viewModelRoom: NoteViewModelRoom = viewModel(factory = factory)

    // Observe and Display Notes
    val notesRoom = viewModelRoom.notes.collectAsState().value


    var searchQuery by remember { mutableStateOf("") }
    val filteredNotes = notesRoom.filter {
        it.title.contains(searchQuery, ignoreCase = true) || it.content.contains(
            searchQuery,
            ignoreCase = true
        )
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar(searchQuery = searchQuery, onSearchQueryChange = { searchQuery = it })
        Spacer(modifier = Modifier.height(8.dp))
        NotesList(notes = filteredNotes.toMutableList())

    }

}

@Composable
fun SearchBar(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChange(it) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = MaterialTheme.shapes.medium)
                .padding(12.dp),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search",
                            style = TextStyle(fontSize = 16.sp, color = Color.DarkGray)
                        )

                    }
                    innerTextField()
                }
            }
        )

    }
}


@Composable
fun NotesList(notes: MutableList<NoteModel>) {
    val model = NotesViewModel(NoteRepositoryImpl())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {

        items(notes) { note ->
            NotesItem(note = note, onDelete = {
                // model.deleteNote(note)
            })

        }
    }
}

@Composable
fun NotesItem(note: NoteModel, onDelete: () -> Unit) {
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