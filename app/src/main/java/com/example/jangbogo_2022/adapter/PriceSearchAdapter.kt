package com.example.jangbogo_2022.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jangbogo_2022.databinding.ItemPriceBinding
import com.example.jangbogo_2022.model.ModelPriceSearch
import java.text.DecimalFormat

class PriceViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)

class PriceSearchAdapter(val data: ArrayList<ModelPriceSearch>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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

        val dec = DecimalFormat("#,###")
        binding.tvPriceItemPrice.text = "${dec.format(d.price)}원"
    }

    override fun getItemCount(): Int {
        return data.size
    }

}