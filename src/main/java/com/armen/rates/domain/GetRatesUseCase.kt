package com.armen.rates.domain

import com.armen.rates.data.RatesData
import io.reactivex.Observable

class GetRatesUseCase(private val imagesRepository: RatesRepository) {
    fun getImages(): Observable<RatesData> = imagesRepository.getRates("EUR")
}

