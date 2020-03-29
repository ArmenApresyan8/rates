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
            "AUD" -> RateItem(R.drawable.ic_euro, key, "Australian dollar", rate, rate * baseValue)
            "BGN" -> RateItem(R.drawable.ic_euro, key, "Bulgarian lev", rate, rate * baseValue)
            else -> RateItem(R.drawable.ic_euro, key, "", rate, rate * baseValue)
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
