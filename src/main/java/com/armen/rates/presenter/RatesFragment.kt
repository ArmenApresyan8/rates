package com.armen.rates.presenter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.armen.rates.R
import com.armen.rates.data.RateItem
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

    private lateinit var adapter: RatesAdapter
    private lateinit var viewModel: RatesViewModel
    private val getRatesUseCase = GetRatesUseCase(RatesRepositoryImpl())
    val mainHandler = Handler(Looper.getMainLooper())

    private val updateTextTask = object : Runnable {
        override fun run() {
            disposable = getRatesUseCase.getRates(viewModel.base)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this@RatesFragment::updateRates)
            mainHandler.postDelayed(this, 1000)
        }
    }
    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.rates_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(RatesViewModel::class.java)
        disposable = getRatesUseCase.getRates(viewModel.base)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::addRates)

        mainHandler.postDelayed(updateTextTask, 1000)

        viewModel.baseValue.observe(viewLifecycleOwner, Observer {
            viewModel.updateValues()
            viewModel.ratesLiveData.value?.let {
                adapter.notifyItemRangeChanged(1, adapter.itemCount - 1, it.subList(1, it.size).toList())
            }
        })

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RatesAdapter(context!!)
        ratesRecyclerView.adapter = adapter
        ratesRecyclerView.layoutManager = layoutManager
        adapter.viewModel = viewModel
        viewModel.ratesLiveData.observe(viewLifecycleOwner, Observer<MutableList<RateItem>> {
            adapter.rateItems = it
        })
    }

    private fun addRates(ratesData: RatesData) {
        viewModel.setRatesData(ratesData)
    }

    private fun updateRates(ratesData: RatesData) {
        viewModel.updateRates(ratesData)
        viewModel.ratesLiveData.value?.let {
            adapter.notifyItemRangeChanged(1, adapter.itemCount - 1, it.subList(1, it.size).toList())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateTextTask)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateTextTask)
    }
}
