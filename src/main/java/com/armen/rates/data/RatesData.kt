package com.armen.rates.data

import com.google.gson.annotations.SerializedName

data class RatesData(@SerializedName("rates") val rates: HashMap<String, Float>)
