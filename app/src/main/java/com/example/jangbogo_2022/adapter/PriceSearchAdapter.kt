package com.example.jangbogo_2022.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jangbogo_2022.databinding.ItemPriceBinding
import com.example.jangbogo_2022.model.ModelPriceSearch
import java.text.DecimalFormat

class PriceViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)

class PriceSearchAdapter(val data: ArrayList<ModelPriceSearch>, val isComma: Boolean, val isWonW: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PriceViewHolder(ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as PriceViewHolder).binding
        val d = data[position]

        binding.tvPriceItemDate.text = d.date
        binding.tvPriceItemStore.text = d.store
        binding.tvPriceItemProduct.text = d.product
        binding.tvPriceItemUnit.text = d.unit

        var won = "원"
        if(isWonW){
            won = "￦"
        }

        if(isComma){
            val dec = DecimalFormat("#,###")
            binding.tvPriceItemPrice.text = dec.format(d.price) + won
        }
        else{
            binding.tvPriceItemPrice.text = d.price.toString() + won
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}