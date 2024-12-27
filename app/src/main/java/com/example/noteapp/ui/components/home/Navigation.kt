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
import androidx.compose.runtime.collectAsState
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
import com.example.noteapp.models.NoteViewModelRoom
import com.example.noteapp.models.NotesViewModel
import com.example.noteapp.repo.reponote.NoteRepositoryImpl
import com.example.noteapp.ui.components.home.navscreens.CreateNoteScreen
import com.example.noteapp.ui.components.home.navscreens.EventsScreen
import com.example.noteapp.ui.components.home.navscreens.NotesScreen
import com.example.noteapp.ui.components.home.navscreens.SearchScreen

data class BottomNavItem(
    val route: String,
    val icon: Int,
    val description: String
)

// Main navigation composable
@Composable
fun Navigation() {
    val navController = rememberNavController()


    Scaffold(
        bottomBar = { MyBottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "notes",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("notes") {
                NotesScreen(
                    viewModel = NotesViewModel(NoteRepositoryImpl()),
                )
            }
            composable("search") { SearchScreen() }
            composable("event") { EventsScreen() }
            composable("create_note") { CreateNoteScreen(navController) }
        }
    }
}

// Bottom navigation bar
@Composable
private fun MyBottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("notes", R.drawable.note, "Notes"),
        BottomNavItem("event", R.drawable.event, "Events"),
        BottomNavItem("create_note", R.drawable.create, "Create Note"),
        BottomNavItem("search", R.drawable.search, "Search")
    )
    val currentRoute =
        navController.currentBackStackEntryFlow.collectAsState(initial = null).value?.destination?.route

    BottomAppBar(
        containerColor = Color.Transparent,
        modifier = Modifier.height(80.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                BottomBarItem(
                    navController = navController,
                    route = item.route,
                    icon = item.icon,
                    description = item.description,
                    isSelected = currentRoute == item.route
                )
            }
        }
    }
}

// Single bottom bar item composable
private @Composable
fun BottomBarItem(
    navController: NavHostController,
    route: String,
    icon: Int,
    description: String,
    isSelected: Boolean
) {

    IconButton(
        onClick = {
            if (navController.currentBackStackEntry?.destination?.route != route) {
                navController.navigate(route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }, modifier = Modifier.size(40.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = description,
            tint = if (isSelected) Color.Blue else Color.Gray,
            modifier = if (isSelected) Modifier.size(40.dp) else Modifier.size(30.dp)
        )
    }
}


