package com.armen.rates.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GetRatesService {

    @GET("https://hiring.revolut.codes/api/android/latest?base=EUR")
    fun getRates(@Query("base") base: String): Observable<RatesData>

}