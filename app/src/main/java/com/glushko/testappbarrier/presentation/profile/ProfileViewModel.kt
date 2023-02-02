package com.glushko.testappbarrier.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.testappbarrier.presentation.profile.model.UserInfoUI
import com.glushko.testappbarrier.domain.aurh.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import com.glushko.testappbarrier.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _liveDataUserInfo = MutableLiveData<Result<UserInfoUI>>()
    val liveDataUserInfo: LiveData<Result<UserInfoUI>> = _liveDataUserInfo

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            _liveDataUserInfo.postValue(Result.Loading)
            delay(2000)
            _liveDataUserInfo.postValue(authRepository.getUser())
        }
    }

    fun signOut(){
        authRepository.signOut()
    }


}