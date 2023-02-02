package com.glushko.testappbarrier.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.glushko.testappbarrier.data.datasource.local.UserAuthStorage
import com.glushko.testappbarrier.domain.aurh.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _liveDataIsAuth = MutableLiveData<Boolean>()
    val liveDataIsAuth: LiveData<Boolean> = _liveDataIsAuth

    init {
        _liveDataIsAuth.postValue(authRepository.isAuth())
    }

}