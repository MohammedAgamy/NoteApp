# NoteApp

This README provides an overview of the components and architecture of the NoteApp project, detailing how the application is structured, its key features, and the purpose of the code provided.

---

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Architecture](#architecture)
4. [Code Breakdown](#code-breakdown)
   - [CreateNoteScreen](#createnotescreen)
   - [NotesScreen](#notesscreen)
   - [SearchScreen](#searchscreen)
   - [Reusable Components](#reusable-components)
5. [Dependencies](#dependencies)
6. [Future Enhancements](#future-enhancements)
7. [GitHub Repository Setup](#github-repository-setup)

---

## Overview

NoteApp is a Kotlin-based Android application built using Jetpack Compose. It allows users to create, view, search, and manage notes. The application supports offline functionality using Room Database and integrates with Firestore for online operations.

---

## Features

- **Create Notes**: Users can create notes with a title and content.
- **View Notes**: Notes are displayed in a list with unique pastel-colored backgrounds.
- **Search Notes**: Users can search notes by title or content.
- **Delete Notes**: Notes can be deleted using a delete button.
- **Offline Support**: Uses Room Database for offline data storage.
- **Online Sync**: Firestore integration for real-time updates when connected to the internet.

---

## Architecture

The application uses a **MVVM (Model-View-ViewModel)** architecture:

- **Model**: Handles data operations using Room and Firestore repositories.
- **ViewModel**: Manages UI-related data and business logic.
- **View**: Jetpack Compose components for UI rendering.

---

## Code Breakdown

### CreateNoteScreen

This screen allows users to create new notes. Key components include:

- **UI Elements**:
  - `TextField` for the note title.
  - `OutlinedTextField` for the note content.
  - A `Button` to save the note.
- **ViewModel Integration**:
  - Uses `NoteViewModelRoom` for Room operations.
  - Includes a timestamp for each note using `generateTimestampId()`.
- **Navigation**:
  - Navigates back to the notes list after saving.

### NotesScreen

Displays a list of notes. Key components include:

- **Dual Source Notes Loading**:
  - Fetches notes from Firestore if online, or from Room if offline.
- **Random Background Colors**:
  - Each note has a randomly generated pastel background for visual variety.
- **Empty State**:
  - Displays a message if no notes are available.

### SearchScreen

Provides functionality to search notes by title or content. Key components include:

- **Search Bar**:
  - A `BasicTextField` for entering the search query.
  - Displays placeholder text when the query is empty.
- **Filtered Notes**:
  - Filters notes dynamically based on the search query.
- **Reusable Notes List**:
  - Displays filtered notes using `LazyColumn` and `NotesItem` components.

### Reusable Components

- **NotesList**:
  - A `LazyColumn` that displays a list of notes.
- **NotesItem**:
  - A `Card` component for each note, displaying its title, content, and a delete button.
- **SearchBar**:
  - A reusable search input field with dynamic placeholder handling.

---

## Dependencies

The following libraries and frameworks are used in the project:

- **Jetpack Compose**: For building the UI.
- **Room**: For offline database storage.
- **Firestore**: For real-time online data synchronization.
- **Material3**: For modern UI design.

---

## Future Enhancements

- **Note Editing**: Add functionality to edit existing notes.
- **Firestore Sync**: Improve sync between Firestore and Room for seamless online-offline transitions.
- **Categories**: Add support for categorizing notes.
- **Improved Search**: Enable advanced search features like filtering by date or tags.

---


## Contribution

Feel free to contribute by forking the repository, making changes, and submitting a pull request. Ensure all code follows the MVVM architecture and adheres to the project's coding guidelines.

## Get Start
<img src ="https://github.com/MohammedAgamy/NoteApp/blob/master/image/WhatsApp%20Image%202025-01-08%20at%2011.41.37%20PM%20(1).jpeg">

## LogIn
<img src ="https://github.com/MohammedAgamy/NoteApp/blob/master/image/WhatsApp%20Image%202025-01-08%20at%2011.41.37%20PM.jpeg">

## Home
<img src ="https://github.com/MohammedAgamy/NoteApp/blob/master/image/WhatsApp%20Image%202025-01-08%20at%2011.41.37%20PM%20(2).jpeg">

## Add Note
<img src ="https://github.com/MohammedAgamy/NoteApp/blob/master/image/WhatsApp%20Image%202025-01-08%20at%2011.41.38%20PM.jpeg">
