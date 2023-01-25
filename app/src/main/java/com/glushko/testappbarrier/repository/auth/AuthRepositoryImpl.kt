package com.glushko.testappbarrier.repository.auth

import com.glushko.testappbarrier.data.model.token.TokenJWT
import com.glushko.testappbarrier.data.model.token.TokenJWTRes
import com.glushko.testappbarrier.data.model.user.UserInfoUI
import com.glushko.testappbarrier.data.network.ApiService
import com.glushko.testappbarrier.utils.NetworkUtils
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import com.glushko.testappbarrier.utils.Result
import java.util.*
import javax.inject.Inject

@BoundTo(supertype = AuthRepository::class, component = SingletonComponent::class)
class AuthRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val apiService: ApiService
): AuthRepository {

    override suspend fun signUpUser(
        firstName: String,
        email: String,
        password: String
    ): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun authenticateUser(email: String, password: String): Result<TokenJWT> {
        val response = networkUtils.getResponse<TokenJWTRes>(
            TokenJWTRes::class.java
        ){
            apiService.authenticateUser(
                token = "${ApiService.BASIC_AUTH} ${basicAuthEncodeBase64(email, password)}"

            )
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.token)
        }
    }

    private fun basicAuthEncodeBase64(email: String, password: String): String {
        return Base64.getEncoder().encodeToString("$email:$password".toByteArray())
    }

    override suspend fun refreshToken(accessToken: String, refreshToken: String): Result<TokenJWT> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): Result<UserInfoUI> {
        TODO("Not yet implemented")
    }


}