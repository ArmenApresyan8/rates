package com.armen.rates.data

import com.armen.rates.domain.RatesRepository
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RatesRepositoryImpl() : RatesRepository {

    private val requestInterface: GetRatesService by lazy {
        Retrofit.Builder()
                .baseUrl("https://hiring.revolut.codes")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GetRatesService::class.java)
    }

    override fun getRates(base: String): Observable<RatesData> = requestInterface.getRates(base)

}