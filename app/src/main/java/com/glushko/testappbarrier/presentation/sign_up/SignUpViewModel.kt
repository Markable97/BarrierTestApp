package com.glushko.testappbarrier.presentation.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.testappbarrier.repository.auth.AuthRepository
import com.glushko.testappbarrier.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _eventSignUp = MutableLiveData<Result<Unit>>()
    val eventSignUp: LiveData<Result<Unit>> = _eventSignUp

    fun singUp(email: String, password: String, name: String) {
        viewModelScope.launch {
            _eventSignUp.postValue(Result.Loading)
            delay(2000)
            _eventSignUp.postValue(
                authRepository.signUpUser(
                    firstName = name,
                    email = email,
                    password = password
                )
            )
        }
    }

}