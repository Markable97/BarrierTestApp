package com.glushko.testappbarrier.data.model.user

import com.glushko.testappbarrier.data.model.Transformable
import com.google.gson.annotations.SerializedName

data class UserInfoReMain(
    val user: UserInfoRes
)

data class UserInfoRes(
    @SerializedName("user_id") val userId: Int? = null,
    @SerializedName("client_id") val clientId: Int? = null,
    @SerializedName("role_id") val roleId: Int? = null,
    @SerializedName("check_in") val checkIn: String? = null,
    @SerializedName("surname") val surname: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("patronymic") val secondName: String? = null,
    @SerializedName("birth_date") val birthDate: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("welcome") val welcome: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("mobile") val mobile: String? = null,
    @SerializedName("uuid") val uuid: String? = null,
    @SerializedName("is_offer") val isOffer: Int? = null,
    @SerializedName("is_enabled") val isEnabled: Int? = null,
    @SerializedName("is_agreed") val isAgreed: Int? = null,
    @SerializedName("last_activity") val lastActivity: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null
) : Transformable<UserInfoUI>{
    override fun transform(): UserInfoUI {
        return UserInfoUI(
            userId = userId,
            clientId = clientId,
            roleId = roleId,
            checkIn = checkIn,
            surname = surname,
            firstName = firstName,
            secondName = secondName,
            birthDate = birthDate,
            gender = gender,
            welcome = welcome,
            email = email,
            mobile = mobile,
            uuid = uuid,
            isOffer = isOffer,
            isEnabled = isEnabled,
            isAgreed = isAgreed,
            lastActivity = lastActivity,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}

