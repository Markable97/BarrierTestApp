package com.glushko.testappbarrier.domain.aurh

import com.glushko.testappbarrier.data.model.token.TokenJWT
import com.glushko.testappbarrier.presentation.profile.model.UserInfoUI
import com.glushko.testappbarrier.utils.Result

interface AuthRepository {

    fun isAuth(): Boolean

    suspend fun signUpUser(
        firstName: String,
        email: String,
        password: String
    ) : Result<Unit>

    suspend fun authenticateUser(
        email: String,
        password: String
    ) : Result<TokenJWT>

    suspend fun getUser() : Result<UserInfoUI>

    fun signOut()

}