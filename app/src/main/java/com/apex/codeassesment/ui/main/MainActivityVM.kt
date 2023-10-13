package com.apex.codeassesment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apex.codeassesment.data.UserRepository
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.util.Resource
import com.apex.codeassesment.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVM @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow(ResponseState<User>())
    val user: StateFlow<ResponseState<User>> = _user

    fun getUser(forceUpdate: Boolean = false) = viewModelScope.launch {
        repository.getUser(forceUpdate).onEach {
            when (it) {
                is Resource.Loading -> {
                    _user.value = ResponseState(isLoading = true)
                }

                is Resource.Error -> {
                    _user.value = ResponseState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    _user.value = ResponseState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    private val _mListUser = MutableStateFlow(ResponseState<List<User>>())
    val mListUser: StateFlow<ResponseState<List<User>>> = _mListUser

    fun getUsers() = viewModelScope.launch {
        repository.getUsers().onEach {
            when (it) {
                is Resource.Loading -> {
                    _mListUser.value = ResponseState(isLoading = true)
                }

                is Resource.Error -> {
                    _mListUser.value = ResponseState(error = it.message ?: "")
                }

                is Resource.Success -> {
                    _mListUser.value = ResponseState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }


}