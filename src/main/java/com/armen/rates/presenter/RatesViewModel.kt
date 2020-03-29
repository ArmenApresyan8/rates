package com.armen.rates.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.armen.rates.R
import com.armen.rates.data.RateItem
import com.armen.rates.data.RatesData

class RatesViewModel : ViewModel() {

    val ratesLiveData: MutableLiveData<MutableList<RateItem>> = MutableLiveData()
    val baseValue: MutableLiveData<Float> = MutableLiveData(100F)
    lateinit var base: String

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
                it.value = baseValue.value!! * it.rate
            }
        }
    }

    fun updateBase(position: Int) {
        ratesLiveData.value?.let {
            for (i in 0 until it.size) {
                it.takeIf { i != 0 && i != position }?.apply {
                    it[i].rate *= it[0].rate / it[position].rate
                }
            }
            it[0].rate = 1 / it[position].rate
            it[position].rate = 1F
            it.add(0, it.removeAt(position))
        }
    }

    fun updateValues() {
        ratesLiveData.value?.let {
            for (data in it) {
                data.value = baseValue.value!! * data.rate
            }
        }
    }

    private fun createRateItem(key: String, rate: Float) {
        val rateItem = when (key) {
            "EUR" -> RateItem(R.drawable.ic_euro, key, "Euro", rate, rate * baseValue.value!!)
            "CAD" -> RateItem(R.drawable.ic_cad, key, "Canadian dollar", rate, rate * baseValue.value!!)
            "BGN" -> RateItem(R.drawable.ic_bgn, key, "Bulgarian lev", rate, rate * baseValue.value!!)
            "BRL" -> RateItem(R.drawable.ic_brl, key, "Brazilian real", rate, rate * baseValue.value!!)
            "AUD" -> RateItem(R.drawable.ic_aud, key, "Australian dollar", rate, rate * baseValue.value!!)
            "CHF" -> RateItem(R.drawable.ic_chf, key, "Swiss franc", rate, rate * baseValue.value!!)
            "CNY" -> RateItem(R.drawable.ic_cny, key, "Renminbi", rate, rate * baseValue.value!!)
            "CZK" -> RateItem(R.drawable.ic_czk, key, "Czech koruna", rate, rate * baseValue.value!!)
            "DKK" -> RateItem(R.drawable.ic_dkk, key, "Danish krone", rate, rate * baseValue.value!!)
            "GBP" -> RateItem(R.drawable.ic_gbp, key, "Pound sterling", rate, rate * baseValue.value!!)
            "HKD" -> RateItem(R.drawable.ic_hkd, key, "Hong Kong dollar", rate, rate * baseValue.value!!)
            "HRK" -> RateItem(R.drawable.ic_hrk, key, "Croatian kuna", rate, rate * baseValue.value!!)
            "HUF" -> RateItem(R.drawable.ic_huf, key, "Hungarian forint", rate, rate * baseValue.value!!)
            "IDR" -> RateItem(R.drawable.ic_idr, key, "Indonesian rupiah", rate, rate * baseValue.value!!)
            "ILS" -> RateItem(R.drawable.ic_ils, key, "Israeli Shekel", rate, rate * baseValue.value!!)
            "INR" -> RateItem(R.drawable.ic_inr, key, "Indian rupee", rate, rate * baseValue.value!!)
            "ISK" -> RateItem(R.drawable.ic_isk, key, "Icelandic króna", rate, rate * baseValue.value!!)
            "JPY" -> RateItem(R.drawable.ic_jpy, key, "Japanese yen", rate, rate * baseValue.value!!)
            "KRW" -> RateItem(R.drawable.ic_krw, key, "South Korean won", rate, rate * baseValue.value!!)
            "MXN" -> RateItem(R.drawable.ic_mxn, key, "Mexican peso", rate, rate * baseValue.value!!)
            "MYR" -> RateItem(R.drawable.ic_myr, key, "Malaysian ringgit", rate, rate * baseValue.value!!)
            "NOK" -> RateItem(R.drawable.ic_nok, key, "Norwegian krone", rate, rate * baseValue.value!!)
            "NZD" -> RateItem(R.drawable.ic_nzd, key, "New Zealand dollar", rate, rate * baseValue.value!!)
            "PHP" -> RateItem(R.drawable.ic_php, key, "Philippine peso", rate, rate * baseValue.value!!)
            "PLN" -> RateItem(R.drawable.ic_pln, key, "Polish złoty", rate, rate * baseValue.value!!)
            "RON" -> RateItem(R.drawable.ic_ron, key, "Romanian leu", rate, rate * baseValue.value!!)
            "RUB" -> RateItem(R.drawable.ic_rub, key, "Russian Rouble", rate, rate * baseValue.value!!)
            "SEK" -> RateItem(R.drawable.ic_sek, key, "Swedish krona", rate, rate * baseValue.value!!)
            "SGD" -> RateItem(R.drawable.ic_sgd, key, "Singapore dollar", rate, rate * baseValue.value!!)
            "THB" -> RateItem(R.drawable.ic_thb, key, "Thai baht", rate, rate * baseValue.value!!)
            "USD" -> RateItem(R.drawable.ic_usd, key, "US dollar", rate, rate * baseValue.value!!)
            "ZAR" -> RateItem(R.drawable.ic_zar, key, "South African rand", rate, rate * baseValue.value!!)
            else -> RateItem(R.drawable.ic_unknown, key, "", rate, rate * baseValue.value!!)
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
