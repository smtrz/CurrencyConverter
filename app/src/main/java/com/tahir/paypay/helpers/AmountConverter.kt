package com.tahir.paypay.helpers

import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import com.tahir.paypay.models.CurrencyRates

object AmountConverter {
    /** returns the list of all the relative currency amount that is binded to the recyclerview. */
    fun convertAllCurrencies(
        currencies: List<Currencies>,
        amount: Double,
        rates: List<CurrenciesRates>,
        selectedCurrency: String
    ): ArrayList<CurrencyRates> {
        var currenciesRate: ArrayList<CurrencyRates> = ArrayList()

        // gets the rate value of user's selected currency
        var selectedValue = rates.first { it.currencyShortName == selectedCurrency }.currencyRate

        for (currency in currencies) {
            // gets the rate value one by one for all the currenccies present

            var iterativeRateList =
                rates.filter { it.currencyShortName == currency.currencyShortName }

            if (iterativeRateList.isNotEmpty()) {
                var relativeRate = iterativeRateList.first().currencyRate
                var total = ((relativeRate / selectedValue) * amount)
                currenciesRate.add(
                    CurrencyRates(
                        currency.currencyShortName,
                        String.format("%.2f", total).toDouble()
                    )
                )
            }
        }
        return currenciesRate
    }
}
