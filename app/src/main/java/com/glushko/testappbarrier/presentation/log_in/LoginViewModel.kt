package com.glushko.testappbarrier.presentation.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.testappbarrier.data.model.token.TokenJWT
import com.glushko.testappbarrier.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.glushko.testappbarrier.utils.Result
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _eventLogIn = MutableLiveData<Result<TokenJWT>>()
    val eventLogIn: LiveData<Result<TokenJWT>> = _eventLogIn

    fun logIn(email: String, password: String){
        viewModelScope.launch {
            _eventLogIn.postValue(Result.Loading)
            _eventLogIn.postValue(authRepository.authenticateUser(email, password))
        }
    }

}