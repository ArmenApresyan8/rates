package com.armen.rates.domain

import com.armen.rates.data.RatesData
import io.reactivex.Observable

class GetRatesUseCase(private val ratesRepository: RatesRepository) {
    fun getRates(base: String): Observable<RatesData> = ratesRepository.getRates(base)
}

