package com.armen.rates.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.armen.rates.R
import com.armen.rates.data.RateItem
import com.armen.rates.data.RatesData

class RatesViewModel : ViewModel() {
    private val ratesLiveData: MutableList<MutableLiveData<RateItem>> = mutableListOf()
    lateinit var base: String
    var baseValue: Float = 100f

    fun setRatesData(ratesData: RatesData) {
        val rates = ratesData.rates
        base = ratesData.base
        createMutableLiveData(base, baseValue)
        for (key in rates.keys) {
            createMutableLiveData(key, rates[key]!!)
        }
    }

    fun updateRates(ratesData: RatesData) {
        val rates = ratesData.rates
        for (key in rates.keys) {
            val data = findLiveData(key)
            data?.let {
                val rateItem = (data.value as RateItem)
                rateItem.value = baseValue * rateItem.value
            }
        }
    }

    private fun createMutableLiveData(key: String, value: Float) {
        val rateItem = createRateItem(key, value)
        val data = MutableLiveData<RateItem>()
        data.value = rateItem
        ratesLiveData.add(data)
    }

    private fun createRateItem(key: String, value: Float) = when(key) {
        "AUD" -> RateItem(R.drawable.ic_euro, key, "Australian dollar", value)
        "BGN" -> RateItem(R.drawable.ic_euro, key, "Bulgarian lev", value)
        else -> RateItem(R.drawable.ic_euro, key, "", value)
    }

    private fun findLiveData(key: String):MutableLiveData<RateItem>? {
        if (key == base) {
            return null
        }
        for (data in ratesLiveData) {
            if ((data.value as RateItem).name == key) {
                return data
            }
        }
        return null
    }
}
