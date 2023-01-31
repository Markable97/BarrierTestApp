package com.glushko.testappbarrier.repository.auth

import com.glushko.testappbarrier.data.model.token.TokenJWT
import com.glushko.testappbarrier.data.model.user.UserInfoUI
import com.glushko.testappbarrier.utils.Result

interface AuthRepository {

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