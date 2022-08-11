package com.tahir.paypay.helpers

import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import okhttp3.ResponseBody
import org.json.JSONObject

object JsonHelper {
    var combinations= emptyList<String>()

    // maps the data into custom model data class, as the data is dynamic
    fun MapCurrencyData(res: ResponseBody?): ArrayList<Currencies> {

        val currencies = arrayListOf<Currencies>()
        var dat = res
        var jsonObject = JSONObject(dat?.string())

        var sIterator: Iterator<String>? = null
        sIterator = jsonObject.keys()
        while (sIterator.hasNext()) {
            // get key
            val key = sIterator.next()
            // get value
            val value: String = jsonObject.getString(key)
            currencies.add(Currencies(currencyShortName = key, currencyFullName = value))
        }
        return currencies
    }
    // maps the data into custom model data class, as the data is dynamic

    fun MapCurrencyRatesData(res: ResponseBody?): ArrayList<CurrenciesRates> {
        val currencies = arrayListOf<CurrenciesRates>()
        var jsonObject = JSONObject(res?.string())

        jsonObject = jsonObject.getJSONObject("rates")

        var sIterator: Iterator<String>? = null
        sIterator = jsonObject.keys()
        while (sIterator.hasNext()) {
            // get key
            val key = sIterator.next()
            // get value
            val value: Double = jsonObject.getDouble(key)
            currencies.add(CurrenciesRates(currencyShortName = key, currencyRate = value))
        }
        return currencies
    }


}
