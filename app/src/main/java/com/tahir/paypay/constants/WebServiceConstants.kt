package com.tahir.paypay.constants

import com.tahir.paypay.BuildConfig


class WebServiceConstants {
    companion object {
        const val BASE_URL = "https://openexchangerates.org/api/"
        const val CURRENCIES = "currencies.json"
        const val LATEST = "latest.json?&app_id=" + BuildConfig.OPEN_EXCHANGE_API_KEY

    }
}