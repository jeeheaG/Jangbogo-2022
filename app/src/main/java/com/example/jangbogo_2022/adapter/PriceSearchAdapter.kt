package com.example.jangbogo_2022.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jangbogo_2022.databinding.ItemPriceBinding
import com.example.jangbogo_2022.model.ModelPriceSearch

class PriceViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)

class PriceSearchAdapter(val data: ArrayList<ModelPriceSearch>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PriceViewHolder(ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as PriceViewHolder).binding
        val d = data[position]

        binding.tvPriceStore.text = d.store
        binding.tvPriceProduct.text = d.product
        binding.tvPriceUnit.text = d.unit
        binding.tvPricePrice.text = d.price
    }

    override fun getItemCount(): Int {
        return data.size
    }

}