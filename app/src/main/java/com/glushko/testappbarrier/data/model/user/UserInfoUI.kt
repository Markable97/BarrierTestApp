package com.glushko.testappbarrier.data.model.user

data class UserInfoUI(
    val userId: Int?,
    val clientId: Int?,
    val roleId: Int?,
    val checkIn: String?,
    val surname: String?,
    val firstName: String?,
    val secondName: String?,
    val birthDate: String?,
    val gender: String?,
    val welcome: String?,
    val email: String?,
    val mobile: String?,
    val uuid: String?,
    val isOffer: Boolean?,
    val isEnabled: Boolean?,
    val isAgreed: Boolean?,
    val lastActivity: String?,
    val createdAt: String?,
    val updatedAt: String?
)