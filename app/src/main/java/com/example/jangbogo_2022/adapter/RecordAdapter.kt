package com.example.jangbogo_2022.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jangbogo_2022.databinding.ItemRecordBinding
import com.example.jangbogo_2022.model.ModelRecordCard

class RecordViewHolder(val binding: ItemRecordBinding) : RecyclerView.ViewHolder(binding.root)

class RecordAdapter(val data: ArrayList<ModelRecordCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return RecordViewHolder(ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as RecordViewHolder).binding
        val d = data[position]

        binding.tvProductName.text = d.name

        Glide.with(binding.root)
            .load(d.imgUrl)
            //.override(120, 120) //크기 조절
            .into(binding.ivImage)
        //binding.ivImage.background = AppCompatResources.getDrawable(context, R.drawable.button_round)
        binding.ivImage.clipToOutline = true
        //TODO : 이미지 테두리 동그랗게 외않되~~~~~

        binding.tvPriceCheap.text = d.cheap
        binding.tvPriceMedium.text = d.medium
        binding.tvPriceExpensive.text = d.expensive
    }

    override fun getItemCount(): Int {
        return data.size
    }


}
