package com.glushko.testappbarrier.presentation.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.testappbarrier.domain.aurh.AuthRepository
import com.glushko.testappbarrier.utils.EventLiveData
import com.glushko.testappbarrier.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _eventSignUp = EventLiveData<Result<Unit>>()
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