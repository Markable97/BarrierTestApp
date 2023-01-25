package com.glushko.testappbarrier.data.network

import com.glushko.testappbarrier.data.model.user.UserCreateReq
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASIC_AUTH = "Basic"
        const val BEARER_AUTH = "Bearer"
        const val EXTRA_AUTH = "Extra"
    }

    @Headers("Content-Type: application/json")
    @POST("v1/users/users")
    suspend fun createUser(@Header("Accept") header: String, @Body user: UserCreateReq): Response<Void>

    @GET("v1/tokens/tokens")
    suspend fun authenticateUser(
        @Header("Authorization") token: String
    ): Response<JsonObject>

    @GET("v1/tokens/refresh")
    suspend fun refreshToken(
        @Header("Authorization") token: String
    ) : Response<JsonObject>

    @GET("v1/users/users/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: String
    ) : Response<JsonObject>

}