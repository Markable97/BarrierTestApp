package com.glushko.testappbarrier.data.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    companion object {
        const val BASIC_AUTH = "Basic"
    }

    @GET("v1/tokens/tokens")
    suspend fun authenticateUser(
        @Header("Authorization") token: String
    ): Response<JsonObject>

}