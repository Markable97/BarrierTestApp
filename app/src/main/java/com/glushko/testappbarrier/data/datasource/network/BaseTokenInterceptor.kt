package com.glushko.testappbarrier.data.datasource.network

import android.content.SharedPreferences
import com.glushko.testappbarrier.utils.Constants
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseTokenInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences) : Interceptor {



    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val url = originalRequest.url

        val headersBuilder = originalRequest.headers.newBuilder()

        if (Constants.METHOD_GET_USERS in url.toString()) {
            sharedPreferences.getString(Constants.KEY_ACCESS_TOKEN, null)?.also { accessToken ->
                headersBuilder.add(Constants.HEADER_AUTH_KEY, "${Constants.HEADER_AUTH_TOKEN_TYPE} $accessToken")
            }
        }

        val request = originalRequest.newBuilder()
            .headers(headersBuilder.build())
            .build()
        return chain.proceed(request)
    }
}
