package com.glushko.testappbarrier.utils

import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.data.network.UnsuccessfulResponseException
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response
import timber.log.Timber
import java.lang.reflect.Type
import javax.inject.Inject


class NetworkUtils @Inject constructor(
    private val gson: Gson
) {

    companion object {
        private const val _400 = 400
        private const val _401 = 401
        private const val _409 = 409
    }

    suspend fun <T> getResponse(type: Type, request: suspend () -> Response<JsonObject>): Result<T> {
        return try {
            val result = request.invoke()
            val body = result.body()
            if (result.isSuccessful) {
                val obj = gson.fromJson<T>(body, type)
                Result.Success(obj)
            } else {
                Result.Error(parseError(result))
            }
        } catch (ex: Exception) {
            Timber.e("${ex.message}")
            Result.Error(ex)
        }
    }

    private fun parseError(result: Response<*>): UnsuccessfulResponseException {
         return when(result.code()){
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