package com.glushko.testappbarrier.data.model.token

import com.google.gson.annotations.SerializedName

data class TokenJWTRes(
    val token: TokenJWT
)

data class TokenJWT(
    @SerializedName("access")
    val accessToken: String,
    @SerializedName("refresh")
    val refreshTToken: String,
    @SerializedName("exp")
    val refreshTime: Long
)
