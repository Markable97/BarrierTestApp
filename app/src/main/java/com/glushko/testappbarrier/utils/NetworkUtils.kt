package com.glushko.testappbarrier.utils

import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.data.model.token.TokenJWT
import com.glushko.testappbarrier.data.model.token.TokenJWTRes
import com.glushko.testappbarrier.data.network.ApiService
import com.glushko.testappbarrier.data.network.UnsuccessfulResponseException
import com.glushko.testappbarrier.repository.user.UserAuthStorage
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject


class NetworkUtils @Inject constructor(
    private val gson: Gson,
    private val apiService: ApiService,
    private val userAuthStorage: UserAuthStorage
) {

    companion object {
        private const val _400 = 400
        private const val _401 = 401
        private const val _409 = 409
    }

    suspend fun getResponseEmptyBody(request: suspend () -> Response<Void>): Result<Unit> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(parseError(result))
            }
        } catch (ex: Exception) {
            Timber.e("${ex.message}")
            Result.Error(ex)
        }
    }

    suspend fun getResponseRefreshToken(request: suspend () -> Response<JsonObject>): Result<TokenJWT> {
        return try {
            val result = request.invoke()
            val body = result.body()
            if (result.isSuccessful) {
                val obj = gson.fromJson(body, TokenJWTRes::class.java)
                userAuthStorage.tokenInfo = obj.token
                Result.Success(obj.token)
            } else {
                Result.Error(parseError(result))
            }
        } catch (ex: Exception) {
            Timber.e("${ex.message}")
            Result.Error(ex)
        }
    }

    suspend fun <T> getResponse(
        type: Type,
        request: suspend () -> Response<JsonObject>
    ): Result<T> {
        return try {
            val result = request.invoke()
            val body = result.body()
            if (result.isSuccessful) {
                val obj = gson.fromJson<T>(body, type)
                Result.Success(obj)
            } else {
                val error = Result.Error(parseError(result))
                if (error.exception is UnsuccessfulResponseException.UnauthorizedException) {
                    val responseRefreshToken = getResponseRefreshToken {
                        val tokenInfo = userAuthStorage.tokenInfo
                        apiService.refreshToken(
                            token = "${ApiService.BEARER_AUTH} ${tokenInfo.accessToken}, " +
                                    "${ApiService.EXTRA_AUTH} ${tokenInfo.refreshTToken}"
                        )
                    }
                    when(responseRefreshToken){
                        is Result.Error -> {
                            Result.Error(responseRefreshToken.exception)
                        }
                        Result.Loading -> {Result.Loading}
                        is Result.Success -> {
                            val resultRetry = request.invoke()
                            val bodyRetry = resultRetry.body()
                            if (resultRetry.isSuccessful) {
                                val obj = gson.fromJson<T>(bodyRetry, type)
                                Result.Success(obj)
                            } else {
                                Result.Error(parseError(resultRetry))
                            }
                        }
                    }
                } else {
                    error
                }
            }
        } catch (ex: Exception) {
            Timber.e("${ex.message}")
            Result.Error(ex)
        }
    }

    private fun parseError(result: Response<*>): UnsuccessfulResponseException {
        return when (result.code()) {
            _409 -> UnsuccessfulResponseException.AlreadyExistsException(
                R.string.network_error_user_already_exists
            )
            _401 -> {
                UnsuccessfulResponseException.UnauthorizedException(
                    R.string.network_error_unauthorized
                )
            }
            else -> UnsuccessfulResponseException.OtherException()

        }
    }

}