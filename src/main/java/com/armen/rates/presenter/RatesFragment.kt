package com.armen.rates.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.armen.rates.R
import com.armen.rates.data.RatesData
import com.armen.rates.data.RatesRepositoryImpl
import com.armen.rates.domain.GetRatesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.rates_fragment.*

class RatesFragment : Fragment() {

    companion object {
        fun newInstance() = RatesFragment()
    }

    private lateinit var viewModel: RatesViewModel
    private val getRatesUseCase = GetRatesUseCase(RatesRepositoryImpl())
    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.rates_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)
        viewModel = ViewModelProvider.NewInstanceFactory().create(RatesViewModel::class.java)
        disposable = getRatesUseCase.getImages()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        ratesRecyclerView.layoutManager = layoutManager
    }

    private fun handleResponse(ratesData: RatesData) {
        viewModel.setRatesData(ratesData)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
