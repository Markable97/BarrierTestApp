package com.glushko.testappbarrier.repository.auth

import com.glushko.testappbarrier.data.model.token.TokenJWT
import com.glushko.testappbarrier.data.model.token.TokenJWTRes
import com.glushko.testappbarrier.data.model.user.UserCreateReq
import com.glushko.testappbarrier.data.model.user.UserInfoReMain
import com.glushko.testappbarrier.data.model.user.UserInfoUI
import com.glushko.testappbarrier.data.network.ApiService
import com.glushko.testappbarrier.repository.user.UserAuthStorage
import com.glushko.testappbarrier.utils.NetworkUtils
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import com.glushko.testappbarrier.utils.Result
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.util.*
import javax.inject.Inject

@BoundTo(supertype = AuthRepository::class, component = SingletonComponent::class)
class AuthRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils,
    private val apiService: ApiService,
    private val userAuthStorage: UserAuthStorage,
    private val gson: Gson
): AuthRepository {

    override suspend fun signUpUser(
        firstName: String,
        email: String,
        password: String
    ): Result<Unit> {
        val response = networkUtils.getResponseEmptyBody {
            apiService.createUser(
                header = "application/json",
                UserCreateReq(
                    firstName = firstName,
                    email = email,
                    password = password
                )
            )
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data)
        }
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
            is Result.Success -> Result.Success(response.data.token.also { tokenInfo ->
                userAuthStorage.tokenInfo = tokenInfo
            })
        }
    }

    private fun basicAuthEncodeBase64(email: String, password: String): String {
        return Base64.getEncoder().encodeToString("$email:$password".toByteArray())
    }

    private fun decodeBase64(data: String): String {
        return String(Base64.getDecoder().decode(data.split(".")[1]))
    }

    override suspend fun getUser(): Result<UserInfoUI> {
        val response = networkUtils.getResponse<UserInfoReMain>(UserInfoReMain::class.java){
            apiService.getUserInfo(getUserId())
        }
        return when(response){
            is Result.Error -> Result.Error(response.exception)
            Result.Loading -> Result.Loading
            is Result.Success -> Result.Success(response.data.user.transform())
        }
    }

    private fun getUserId(): String{
        val accessToken = userAuthStorage.tokenInfo.accessToken
        val jsonInfo = decodeBase64(accessToken)
        val jsonObject = gson.fromJson(jsonInfo, JsonObject::class.java)
        val userId = jsonObject.get("uid").asString
        return userId
    }


}