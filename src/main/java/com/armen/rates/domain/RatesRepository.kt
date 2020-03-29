package com.armen.rates.domain

import com.armen.rates.data.RatesData
import io.reactivex.Observable


interface RatesRepository {
    fun getRates(base: String): Observable<RatesData>
}