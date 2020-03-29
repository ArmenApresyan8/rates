package com.armen.rates.data

import com.google.gson.annotations.SerializedName

data class RatesData(
        @SerializedName("baseCurrency") val base: String,
        @SerializedName("rates") val rates: HashMap<String, Float>)
