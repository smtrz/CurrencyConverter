package com.tahir.paypay.api


import com.tahir.paypay.constants.WebServiceConstants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

/**
 * openexchange Service contains suspend methods for the network calls
 */
interface CurrencyConversionService {

    @GET(WebServiceConstants.CURRENCIES)
    suspend fun getCurrencies(

    ): Response<ResponseBody>

    @GET(WebServiceConstants.LATEST)
    suspend fun getLatestRates(
    ): Response<ResponseBody>


}