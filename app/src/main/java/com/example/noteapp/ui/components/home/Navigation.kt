package com.example.noteapp.ui.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.noteapp.R
import com.example.noteapp.ui.components.home.navscreens.CreateNoteScreen
import com.example.noteapp.ui.components.home.navscreens.EventsScreen
import com.example.noteapp.ui.components.home.navscreens.NotesScreen
import com.example.noteapp.ui.components.home.navscreens.SearchScreen

@Composable
fun Navigation (){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "notes",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("notes") { NotesScreen() }
            composable("search") { SearchScreen() }
            composable("event") { EventsScreen() }
            composable("create_note") { CreateNoteScreen() }
        }
    }
}


@Composable
private fun MyBottomBar(navController: NavHostController) {
    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier.height(56.dp) // Optional: Set consistent height
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly, // Ensure even spacing
            verticalAlignment = Alignment.CenterVertically
        )
        {
            BottomBarItem(navController, "notes", R.drawable.note, "Notes")
            BottomBarItem(navController, "event", R.drawable.event, "Events")
            BottomBarItem(navController, "create_note", R.drawable.create, "Create Note")
            BottomBarItem(navController, "search", R.drawable.search, "Search")
        }
    }


}


private @Composable
fun BottomBarItem(
    navController: NavHostController,
    route: String,
    icon: Int,
    description: String
) {
    IconButton(
        onClick = { navController.navigate(route) }, modifier = Modifier.size(12.dp)
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = description)
    }
}




