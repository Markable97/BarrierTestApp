package com.glushko.testappbarrier.repository.user

import android.content.SharedPreferences
import androidx.core.content.edit
import com.glushko.testappbarrier.data.model.token.TokenJWT
import com.glushko.testappbarrier.utils.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAuthStorage @Inject constructor(
    private val sharedPrefs: SharedPreferences,
) {

    private var cashTokenInfo: TokenJWT? = null

    val isAuth = tokenInfo.accessToken.isNotBlank()

    var tokenInfo: TokenJWT
        get() = cashTokenInfo ?: readTokenInfo()
        set(value) = writeTokenInfo(value)

    private fun writeTokenInfo(tokenInfo: TokenJWT) = sharedPrefs.edit{
        cashTokenInfo = tokenInfo
        putString(Constants.KEY_ACCESS_TOKEN, tokenInfo.accessToken)
        putString(Constants.KEY_REFRESH_TOKEN, tokenInfo.refreshTToken)
        putLong(Constants.KEY_REFRESH_TIME, tokenInfo.refreshTime)
    }

    private fun readTokenInfo() = sharedPrefs.run {
        val token = TokenJWT(
            accessToken = getString(Constants.KEY_ACCESS_TOKEN, "") ?: "",
            refreshTToken = getString(Constants.KEY_REFRESH_TOKEN, "") ?: "",
            refreshTime = getLong(Constants.KEY_REFRESH_TIME, 0L)
        )
        cashTokenInfo = token
        token
    }

    fun clear() = sharedPrefs.edit {
        cashTokenInfo = null
        remove(Constants.KEY_ACCESS_TOKEN)
        remove(Constants.KEY_REFRESH_TOKEN)
        remove(Constants.KEY_REFRESH_TIME)
    }

}