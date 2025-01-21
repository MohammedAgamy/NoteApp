package com.example.noteapp.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.Effect.SnackbarEffect
import com.example.noteapp.data.mvi.RegisterModel
import com.example.noteapp.data.mvi.UserIntent
import com.example.noteapp.data.mvi.UserViewState
import com.example.noteapp.repo.reporegister.RepositoryRegister
import com.example.noteapp.repo.reporegister.RepositoryRegisterImpl
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val repo: RepositoryRegister) : ViewModel() {
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _nameError = MutableStateFlow(false)
    val nameError: StateFlow<Boolean> = _nameError

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone
    private val _phoneError = MutableStateFlow(false)
    val phoneError: StateFlow<Boolean> = _phoneError

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError


    private val _registerResult = MutableStateFlow<Result<Boolean>?>(null)
    val registerResult: StateFlow<Result<Boolean>?> = _registerResult

////////////////////////////////
    private val repository: RepositoryRegister = RepositoryRegisterImpl()

    private val _viewState = MutableStateFlow(UserViewState())
    val viewState: StateFlow<UserViewState> = _viewState

    // Effect channel to handle snackbar events
    private val _effectChannel = Channel<SnackbarEffect>()
    val effectFlow: Flow<SnackbarEffect> = _effectChannel.receiveAsFlow()

    private var recentlyDeletedUser: RegisterModel? = null


    init {
        handleIntent(UserIntent.LoadUsers)
    }

    private fun handleIntent(intent : UserIntent) {
      when(intent){
          is UserIntent.AddUserView -> addUser(intent.name, intent.email , intent.phone, intent.password)
          UserIntent.LoadUsers -> loadUser()
          UserIntent.UndoDelete -> TODO()
          is UserIntent.UpdateEmail -> TODO()
          is UserIntent.UpdateName -> TODO()
      }
    }

     fun addUser(name:String , email:String , phone: String , password:String) {
        if (name.isBlank() || email.isBlank()) {
            _viewState.value =
                _viewState.value.copy(nameError = name.isBlank(), emailError = email.isBlank())
            return
        }

        viewModelScope.launch {
            val newUser = RegisterModel(id = (0..1000).random(), name = name, email = email , phone = phone , password = password)
            repository.login(newUser)
            _viewState.value = _viewState.value.copy(name = "", email = "")
         //   loadUsers() // Reload users after adding
            _effectChannel.send(SnackbarEffect.ShowSnackbar("User added successfully"))
        }
    }

      fun loadUser()
     {
          viewModelScope.launch {
              _effectChannel.send(SnackbarEffect.ShowSnackbar("User added successfully"))

          }
     }

    fun viewModelRegister() {
        viewModelScope.launch {
            _registerResult.value = repo.login(
                RegisterModel(
                    _name.value,
                    _phone.value,
                    _email.value,
                    _password.value,
                    nameError = true,
                    phoneError = true,
                    emailError = true,
                    passwordError = true,
                    (0..1000).random()
                )
            )
        }
    }

    fun updateName(input: String) {
        _name.value = input
        _nameError.value =
            input.isBlank()
    }

    fun updatePhone(input: String) {
        _phone.value = input
        _phoneError.value = input.isBlank()  // pass cannot be empty
    }


    fun updateEmail(input: String) {
        _email.value = input
        _emailError.value =
            input.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(input)
                .matches()  // Check email format
    }

    fun updatePassword(input: String) {
        _password.value = input
        _passwordError.value = input.isBlank()  // pass cannot be empty
    }


}