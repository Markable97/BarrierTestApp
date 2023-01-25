package com.glushko.testappbarrier.data.model.user

import com.google.gson.annotations.SerializedName

data class UserCreateReq(
    @SerializedName("first_name") var firstName: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("is_agreed") var isAgreed: Boolean = true

)
