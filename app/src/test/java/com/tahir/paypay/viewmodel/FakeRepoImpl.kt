package com.tahir.paypay.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tahir.paypay.api.CurrencyConversionService
import com.tahir.paypay.db.Currencies
import com.tahir.paypay.db.CurrenciesRates
import com.tahir.paypay.db.CurrencyDao
import com.tahir.paypay.helpers.JsonHelper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

/**
 * fake Implementation for Currency Converter Service
 */
class FakeRepoImpl() : CurrencyConversionService, CurrencyDao {
    var currencyResponseBody: ResponseBody
    var ratesResponseBody: ResponseBody
    var currencies =
        "{\n" +
                "  \"AED\": \"United Arab Emirates Dirham\",\n" +
                "  \"AFN\": \"Afghan Afghani\",\n" +
                "  \"ALL\": \"Albanian Lek\",\n" +
                "  \"AMD\": \"Armenian Dram\",\n" +
                "  \"ANG\": \"Netherlands Antillean Guilder\",\n" +
                "  \"AOA\": \"Angolan Kwanza\",\n" +
                "  \"ARS\": \"Argentine Peso\",\n" +
                "  \"AUD\": \"Australian Dollar\",\n" +
                "  \"AWG\": \"Aruban Florin\",\n" +
                "  \"AZN\": \"Azerbaijani Manat\",\n" +
                "  \"BAM\": \"Bosnia-Herzegovina Convertible Mark\",\n" +
                "  \"BBD\": \"Barbadian Dollar\",\n" +
                "  \"BDT\": \"Bangladeshi Taka\",\n" +
                "  \"BGN\": \"Bulgarian Lev\",\n" +
                "  \"BHD\": \"Bahraini Dinar\",\n" +
                "  \"BIF\": \"Burundian Franc\",\n" +
                "  \"BMD\": \"Bermudan Dollar\",\n" +
                "  \"BND\": \"Brunei Dollar\",\n" +
                "  \"BOB\": \"Bolivian Boliviano\",\n" +
                "  \"BRL\": \"Brazilian Real\",\n" +
                "  \"BSD\": \"Bahamian Dollar\",\n" +
                "  \"BTC\": \"Bitcoin\",\n" +
                "  \"BTN\": \"Bhutanese Ngultrum\",\n" +
                "  \"BWP\": \"Botswanan Pula\",\n" +
                "  \"BYN\": \"Belarusian Ruble\",\n" +
                "  \"BZD\": \"Belize Dollar\",\n" +
                "  \"CAD\": \"Canadian Dollar\",\n" +
                "  \"CDF\": \"Congolese Franc\",\n" +
                "  \"CHF\": \"Swiss Franc\",\n" +
                "  \"CLF\": \"Chilean Unit of Account (UF)\",\n" +
                "  \"CLP\": \"Chilean Peso\",\n" +
                "  \"CNH\": \"Chinese Yuan (Offshore)\",\n" +
                "  \"CNY\": \"Chinese Yuan\",\n" +
                "  \"COP\": \"Colombian Peso\",\n" +
                "  \"CRC\": \"Costa Rican Colón\",\n" +
                "  \"CUC\": \"Cuban Convertible Peso\",\n" +
                "  \"CUP\": \"Cuban Peso\",\n" +
                "  \"CVE\": \"Cape Verdean Escudo\",\n" +
                "  \"CZK\": \"Czech Republic Koruna\",\n" +
                "  \"DJF\": \"Djiboutian Franc\",\n" +
                "  \"DKK\": \"Danish Krone\",\n" +
                "  \"DOP\": \"Dominican Peso\",\n" +
                "  \"DZD\": \"Algerian Dinar\",\n" +
                "  \"EGP\": \"Egyptian Pound\",\n" +
                "  \"ERN\": \"Eritrean Nakfa\",\n" +
                "  \"ETB\": \"Ethiopian Birr\",\n" +
                "  \"EUR\": \"Euro\",\n" +
                "  \"FJD\": \"Fijian Dollar\",\n" +
                "  \"FKP\": \"Falkland Islands Pound\",\n" +
                "  \"GBP\": \"British Pound Sterling\",\n" +
                "  \"GEL\": \"Georgian Lari\",\n" +
                "  \"GGP\": \"Guernsey Pound\",\n" +
                "  \"GHS\": \"Ghanaian Cedi\",\n" +
                "  \"GIP\": \"Gibraltar Pound\",\n" +
                "  \"GMD\": \"Gambian Dalasi\",\n" +
                "  \"GNF\": \"Guinean Franc\",\n" +
                "  \"GTQ\": \"Guatemalan Quetzal\",\n" +
                "  \"GYD\": \"Guyanaese Dollar\",\n" +
                "  \"HKD\": \"Hong Kong Dollar\",\n" +
                "  \"HNL\": \"Honduran Lempira\",\n" +
                "  \"HRK\": \"Croatian Kuna\",\n" +
                "  \"HTG\": \"Haitian Gourde\",\n" +
                "  \"HUF\": \"Hungarian Forint\",\n" +
                "  \"IDR\": \"Indonesian Rupiah\",\n" +
                "  \"ILS\": \"Israeli New Sheqel\",\n" +
                "  \"IMP\": \"Manx pound\",\n" +
                "  \"INR\": \"Indian Rupee\",\n" +
                "  \"IQD\": \"Iraqi Dinar\",\n" +
                "  \"IRR\": \"Iranian Rial\",\n" +
                "  \"ISK\": \"Icelandic Króna\",\n" +
                "  \"JEP\": \"Jersey Pound\",\n" +
                "  \"JMD\": \"Jamaican Dollar\",\n" +
                "  \"JOD\": \"Jordanian Dinar\",\n" +
                "  \"JPY\": \"Japanese Yen\",\n" +
                "  \"KES\": \"Kenyan Shilling\",\n" +
                "  \"KGS\": \"Kyrgystani Som\",\n" +
                "  \"KHR\": \"Cambodian Riel\",\n" +
                "  \"KMF\": \"Comorian Franc\",\n" +
                "  \"KPW\": \"North Korean Won\",\n" +
                "  \"KRW\": \"South Korean Won\",\n" +
                "  \"KWD\": \"Kuwaiti Dinar\",\n" +
                "  \"KYD\": \"Cayman Islands Dollar\",\n" +
                "  \"KZT\": \"Kazakhstani Tenge\",\n" +
                "  \"LAK\": \"Laotian Kip\",\n" +
                "  \"LBP\": \"Lebanese Pound\",\n" +
                "  \"LKR\": \"Sri Lankan Rupee\",\n" +
                "  \"LRD\": \"Liberian Dollar\",\n" +
                "  \"LSL\": \"Lesotho Loti\",\n" +
                "  \"LYD\": \"Libyan Dinar\",\n" +
                "  \"MAD\": \"Moroccan Dirham\",\n" +
                "  \"MDL\": \"Moldovan Leu\",\n" +
                "  \"MGA\": \"Malagasy Ariary\",\n" +
                "  \"MKD\": \"Macedonian Denar\",\n" +
                "  \"MMK\": \"Myanma Kyat\",\n" +
                "  \"MNT\": \"Mongolian Tugrik\",\n" +
                "  \"MOP\": \"Macanese Pataca\",\n" +
                "  \"MRU\": \"Mauritanian Ouguiya\",\n" +
                "  \"MUR\": \"Mauritian Rupee\",\n" +
                "  \"MVR\": \"Maldivian Rufiyaa\",\n" +
                "  \"MWK\": \"Malawian Kwacha\",\n" +
                "  \"MXN\": \"Mexican Peso\",\n" +
                "  \"MYR\": \"Malaysian Ringgit\",\n" +
                "  \"MZN\": \"Mozambican Metical\",\n" +
                "  \"NAD\": \"Namibian Dollar\",\n" +
                "  \"NGN\": \"Nigerian Naira\",\n" +
                "  \"NIO\": \"Nicaraguan Córdoba\",\n" +
                "  \"NOK\": \"Norwegian Krone\",\n" +
                "  \"NPR\": \"Nepalese Rupee\",\n" +
                "  \"NZD\": \"New Zealand Dollar\",\n" +
                "  \"OMR\": \"Omani Rial\",\n" +
                "  \"PAB\": \"Panamanian Balboa\",\n" +
                "  \"PEN\": \"Peruvian Nuevo Sol\",\n" +
                "  \"PGK\": \"Papua New Guinean Kina\",\n" +
                "  \"PHP\": \"Philippine Peso\",\n" +
                "  \"PKR\": \"Pakistani Rupee\",\n" +
                "  \"PLN\": \"Polish Zloty\",\n" +
                "  \"PYG\": \"Paraguayan Guarani\",\n" +
                "  \"QAR\": \"Qatari Rial\",\n" +
                "  \"RON\": \"Romanian Leu\",\n" +
                "  \"RSD\": \"Serbian Dinar\",\n" +
                "  \"RUB\": \"Russian Ruble\",\n" +
                "  \"RWF\": \"Rwandan Franc\",\n" +
                "  \"SAR\": \"Saudi Riyal\",\n" +
                "  \"SBD\": \"Solomon Islands Dollar\",\n" +
                "  \"SCR\": \"Seychellois Rupee\",\n" +
                "  \"SDG\": \"Sudanese Pound\",\n" +
                "  \"SEK\": \"Swedish Krona\",\n" +
                "  \"SGD\": \"Singapore Dollar\",\n" +
                "  \"SHP\": \"Saint Helena Pound\",\n" +
                "  \"SLL\": \"Sierra Leonean Leone\",\n" +
                "  \"SOS\": \"Somali Shilling\",\n" +
                "  \"SRD\": \"Surinamese Dollar\",\n" +
                "  \"SSP\": \"South Sudanese Pound\",\n" +
                "  \"STD\": \"São Tomé and Príncipe Dobra (pre-2018)\",\n" +
                "  \"STN\": \"São Tomé and Príncipe Dobra\",\n" +
                "  \"SVC\": \"Salvadoran Colón\",\n" +
                "  \"SYP\": \"Syrian Pound\",\n" +
                "  \"SZL\": \"Swazi Lilangeni\",\n" +
                "  \"THB\": \"Thai Baht\",\n" +
                "  \"TJS\": \"Tajikistani Somoni\",\n" +
                "  \"TMT\": \"Turkmenistani Manat\",\n" +
                "  \"TND\": \"Tunisian Dinar\",\n" +
                "  \"TOP\": \"Tongan Pa'anga\",\n" +
                "  \"TRY\": \"Turkish Lira\",\n" +
                "  \"TTD\": \"Trinidad and Tobago Dollar\",\n" +
                "  \"TWD\": \"New Taiwan Dollar\",\n" +
                "  \"TZS\": \"Tanzanian Shilling\",\n" +
                "  \"UAH\": \"Ukrainian Hryvnia\",\n" +
                "  \"UGX\": \"Ugandan Shilling\",\n" +
                "  \"USD\": \"United States Dollar\",\n" +
                "  \"UYU\": \"Uruguayan Peso\",\n" +
                "  \"UZS\": \"Uzbekistan Som\",\n" +
                "  \"VEF\": \"Venezuelan Bolívar Fuerte (Old)\",\n" +
                "  \"VES\": \"Venezuelan Bolívar Soberano\",\n" +
                "  \"VND\": \"Vietnamese Dong\",\n" +
                "  \"VUV\": \"Vanuatu Vatu\",\n" +
                "  \"WST\": \"Samoan Tala\",\n" +
                "  \"XAF\": \"CFA Franc BEAC\",\n" +
                "  \"XAG\": \"Silver Ounce\",\n" +
                "  \"XAU\": \"Gold Ounce\",\n" +
                "  \"XCD\": \"East Caribbean Dollar\",\n" +
                "  \"XDR\": \"Special Drawing Rights\",\n" +
                "  \"XOF\": \"CFA Franc BCEAO\",\n" +
                "  \"XPD\": \"Palladium Ounce\",\n" +
                "  \"XPF\": \"CFP Franc\",\n" +
                "  \"XPT\": \"Platinum Ounce\",\n" +
                "  \"YER\": \"Yemeni Rial\",\n" +
                "  \"ZAR\": \"South African Rand\",\n" +
                "  \"ZMW\": \"Zambian Kwacha\",\n" +
                "  \"ZWL\": \"Zimbabwean Dollar\"\n" +
                "}"
    var rates =
        "{\"disclaimer\": \"Usage subject to terms: https://openexchangerates.org/terms\"," +
                "  \"license\": \"https://openexchangerates.org/license\"," +
                "  \"timestamp\": 1659722400," +
                "  \"base\": \"USD\"," +
                "  \"rates\": {" +
                "    \"AED\": 3.6731," +
                "    \"AFN\": 90.999992," +
                "    \"ALL\": 114.05," +
                "    \"AMD\": 406.539648," +
                "    \"ANG\": 1.803244," +
                "    \"AOA\": 431.1421," +
                "    \"ARS\": 132.916," +
                "  }" +
                "}"

    init {


        currencyResponseBody = currencies.toResponseBody("application/json".toMediaTypeOrNull())

        ratesResponseBody = rates.toResponseBody("application/json".toMediaTypeOrNull())
    }

    private var liveRate = MutableLiveData<List<CurrenciesRates>>()
    override suspend fun getCurrencies(): Response<ResponseBody> {
        //  print(currencyResponseBody.string())
        return Response.success(currencyResponseBody)
    }

    override suspend fun getLatestRates(): Response<ResponseBody> {


        return Response.success(ratesResponseBody)
    }

    override suspend fun insertCurrencies(currencies: ArrayList<Currencies>): List<Long> {

        var longlist: ArrayList<Long> = ArrayList()
        longlist.add(1)
        return longlist.toList()
    }

    override suspend fun getAllCurrencies(): List<Currencies> {
        return JsonHelper.MapCurrencyData(currencyResponseBody)

    }

    override suspend fun insertCurrencyRates(rates: ArrayList<CurrenciesRates>): List<Long> {
        liveRate.postValue(JsonHelper.MapCurrencyRatesData(ratesResponseBody).toList())
        return listOf(rates.size.toLong())
    }

    override fun getAllRates(): LiveData<List<CurrenciesRates>> {
        return liveRate
    }
}

