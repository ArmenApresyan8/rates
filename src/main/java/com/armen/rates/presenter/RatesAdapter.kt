package com.armen.rates.presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.armen.rates.R
import com.armen.rates.data.RateItem
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.rate_item.view.*

class RatesAdapter(private val context: Context) : RecyclerView.Adapter<RatesAdapter.RateViewHolder>() {


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

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val rateItem = rateItems[position]
        glide.load(rateItem.resId).apply(RequestOptions.circleCropTransform()).into(holder.rateImage)
        holder.rateName.text = rateItem.name
        holder.rateDescription.text = rateItem.description
        holder.rateEditText.setText(rateItem.value.toString())
    }

    class RateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rateImage: ImageView = itemView.rateImage
        val rateName: TextView = itemView.rateName
        val rateDescription: TextView = itemView.rateDescription
        val rateEditText: EditText = itemView.rateEditText
    }

}