package com.glushko.testappbarrier.data.network

import androidx.annotation.StringRes
import com.glushko.testappbarrier.R

sealed class UnsuccessfulResponseException(
    @StringRes val errorMessage: Int
) : Exception() {

    class UnauthorizedException(
        errorMessage: Int
    ) : UnsuccessfulResponseException(errorMessage)

    class AlreadyExistsException(
        errorMessage: Int
    ) : UnsuccessfulResponseException(errorMessage)

    class OtherException(
        errorMessage: Int = R.string.network_error_default
    ) : UnsuccessfulResponseException(errorMessage)

}