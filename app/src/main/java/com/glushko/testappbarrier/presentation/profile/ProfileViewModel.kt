package com.glushko.testappbarrier.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glushko.testappbarrier.data.model.user.UserInfoUI
import com.glushko.testappbarrier.repository.auth.AuthRepository
import com.glushko.testappbarrier.repository.user.UserAuthStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import com.glushko.testappbarrier.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userAuthStorage: UserAuthStorage
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
        userAuthStorage.clear()
    }


}