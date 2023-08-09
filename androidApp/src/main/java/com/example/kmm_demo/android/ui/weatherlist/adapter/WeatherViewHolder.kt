package com.example.kmm_demo.android.ui.weatherlist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.kmm_demo.android.data.dto.WeatherItem
import com.example.kmm_demo.android.databinding.WeatherItemBinding

class WeatherViewHolder(private val itemBinding: WeatherItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: WeatherItem) {
        val degree = "\u00B0"
        itemBinding.currTempTxt.text = "${item.curr_temp}${degree}C in current location"
        itemBinding.descriptionTxt.text = item.description
        itemBinding.minTempTxt.text = "${item.min_temp}${degree}C"
        itemBinding.maxTempTxt.text = "${item.max_temp}${degree}C"
    }

}