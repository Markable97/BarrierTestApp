package com.glushko.testappbarrier.data.network

import androidx.annotation.StringRes

class UnsuccessfulResponseException(
    @StringRes errorMessage: Int
) : Exception()