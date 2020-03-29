package com.armen.rates.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.armen.rates.R
import com.armen.rates.data.RateItem
import com.armen.rates.data.RatesData

class RatesViewModel : ViewModel() {
    val ratesLiveData: MutableLiveData<MutableList<RateItem>> = MutableLiveData()
    lateinit var base: String
    var baseValue: Float = 100f

    fun setRatesData(ratesData: RatesData) {
        val rates = ratesData.rates
        base = ratesData.base
        ratesLiveData.value = mutableListOf()
        createRateItem(base, 1F)
        for (key in rates.keys) {
            createRateItem(key, rates[key]!!)
        }
    }

    fun updateRates(ratesData: RatesData) {
        val rates = ratesData.rates
        for (key in rates.keys) {
            val data = findRateItem(key)
            data?.let {
                it.value = baseValue * it.rate
            }
        }
    }

    private fun createRateItem(key: String, rate: Float) {
        val rateItem = when(key) {
            "EUR" -> RateItem(R.drawable.ic_euro, key, "Euro", rate, rate * baseValue)
            "CAD" -> RateItem(R.drawable.ic_cad, key, "Canadian dollar", rate, rate * baseValue)
            "BGN" -> RateItem(R.drawable.ic_bgn, key, "Bulgarian lev", rate, rate * baseValue)
            "BRL" -> RateItem(R.drawable.ic_brl, key, "Brazilian real", rate, rate * baseValue)
            "AUD" -> RateItem(R.drawable.ic_aud, key, "Australian dollar", rate, rate * baseValue)
            "CHF" -> RateItem(R.drawable.ic_chf, key, "Swiss franc", rate, rate * baseValue)
            "CNY" -> RateItem(R.drawable.ic_cny, key, "Renminbi", rate, rate * baseValue)
            "CZK" -> RateItem(R.drawable.ic_czk, key, "Czech koruna", rate, rate * baseValue)
            "DKK" -> RateItem(R.drawable.ic_dkk, key, "Danish krone", rate, rate * baseValue)
            "GBP" -> RateItem(R.drawable.ic_gbp, key, "Pound sterling", rate, rate * baseValue)
            "HKD" -> RateItem(R.drawable.ic_hkd, key, "Hong Kong dollar", rate, rate * baseValue)
            "HRK" -> RateItem(R.drawable.ic_hrk, key, "Croatian kuna", rate, rate * baseValue)
            "HUF" -> RateItem(R.drawable.ic_huf, key, "Hungarian forint", rate, rate * baseValue)
            "IDR" -> RateItem(R.drawable.ic_idr, key, "Indonesian rupiah", rate, rate * baseValue)
            "ILS" -> RateItem(R.drawable.ic_ils, key, "Israeli Shekel", rate, rate * baseValue)
            "INR" -> RateItem(R.drawable.ic_inr, key, "Indian rupee", rate, rate * baseValue)
            "ISK" -> RateItem(R.drawable.ic_isk, key, "Icelandic króna", rate, rate * baseValue)
            "JPY" -> RateItem(R.drawable.ic_jpy, key, "Japanese yen", rate, rate * baseValue)
            "KRW" -> RateItem(R.drawable.ic_krw, key, "South Korean won", rate, rate * baseValue)
            "MXN" -> RateItem(R.drawable.ic_mxn, key, "Mexican peso", rate, rate * baseValue)
            "MYR" -> RateItem(R.drawable.ic_myr, key, "Malaysian ringgit", rate, rate * baseValue)
            "NOK" -> RateItem(R.drawable.ic_nok, key, "Norwegian krone", rate, rate * baseValue)
            "NZD" -> RateItem(R.drawable.ic_nzd, key, "New Zealand dollar", rate, rate * baseValue)
            "PHP" -> RateItem(R.drawable.ic_php, key, "Philippine peso", rate, rate * baseValue)
            "PLN" -> RateItem(R.drawable.ic_pln, key, "Polish złoty", rate, rate * baseValue)
            "RON" -> RateItem(R.drawable.ic_ron, key, "Romanian leu", rate, rate * baseValue)
            "RUB" -> RateItem(R.drawable.ic_rub, key, "Russian Rouble", rate, rate * baseValue)
            "SEK" -> RateItem(R.drawable.ic_sek, key, "Swedish krona", rate, rate * baseValue)
            "SGD" -> RateItem(R.drawable.ic_sgd, key, "Singapore dollar", rate, rate * baseValue)
            "THB" -> RateItem(R.drawable.ic_thb, key, "Thai baht", rate, rate * baseValue)
            "USD" -> RateItem(R.drawable.ic_usd, key, "US dollar", rate, rate * baseValue)
            "ZAR" -> RateItem(R.drawable.ic_zar, key, "South African rand", rate, rate * baseValue)
            else -> RateItem(R.drawable.ic_unknown, key, "", rate, rate * baseValue)
        }
        ratesLiveData.value?.add(rateItem)
    }

    private fun findRateItem(key: String): RateItem? {
        if (key == base) {
            return null
        }
        for (data in ratesLiveData.value!!) {
            if (data.name == key) {
                return data
            }
        }
        return null
    }
}
