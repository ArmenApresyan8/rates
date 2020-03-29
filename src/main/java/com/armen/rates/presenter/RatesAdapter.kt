package com.armen.rates.presenter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.armen.rates.R
import com.armen.rates.data.RateItem
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.rate_item.view.*

class RatesAdapter(private val context: Context) : RecyclerView.Adapter<RatesAdapter.RateViewHolder>() {


    var viewModel: RatesViewModel? = null
    private var recyclerView: RecyclerView? = null

    private val glide: RequestManager by lazy {
        Glide.with(context)
    }

    var rateItems: List<RateItem> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder =
            RateViewHolder(LayoutInflater.from(context).inflate(R.layout.rate_item, parent, false))

    override fun getItemCount() = rateItems.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rateItem = rateItems[position]
        glide.load(rateItem.resId).apply(RequestOptions.circleCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.rateImage)
        holder.rateName.text = rateItem.name
        holder.rateDescription.text = rateItem.description
        holder.rateEditText.setText("%.2f".format(rateItem.value))

        holder.rateEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel?.let {
                    if (rateItem.name == it.base && holder.adapterPosition == 0) {
                        try {
                            var value = 0F
                            if (!s.isNullOrEmpty()) {
                                value = s.toString().toFloat()
                            }
                            if (it.baseValue.value != value) {
                                it.baseValue.value = value
                            }
                        } catch (ex: NumberFormatException) {
                            ex.printStackTrace()
                        }
                    }
                }
            }
        })
        holder.rateEditText.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) moveToTop(position, rateItem) }

        holder.itemView.setOnClickListener {
            moveToTop(position, rateItem)
        }
    }

    private fun moveToTop(position: Int, rateItem: RateItem) {
        viewModel?.let {
            if (it.base != rateItem.name) {
                it.updateBase(position)
                notifyItemMoved(position, 0)
                recyclerView?.scrollToPosition(0)
                it.baseValue.value = rateItem.value
                it.base = rateItem.name
            }
        }
    }

    class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rateImage: ImageView = itemView.rateImage
        val rateName: TextView = itemView.rateName
        val rateDescription: TextView = itemView.rateDescription
        val rateEditText: EditText = itemView.rateEditText
    }

}