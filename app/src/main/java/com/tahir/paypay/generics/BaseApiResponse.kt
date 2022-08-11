package com.tahir.paypay.generics

import retrofit2.Response

/**
 * abstract class with method for calling the API and return the response wrapped in sealed class of
 * Network Result
 */
abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {

            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }

            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {

            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed $errorMessage")
}