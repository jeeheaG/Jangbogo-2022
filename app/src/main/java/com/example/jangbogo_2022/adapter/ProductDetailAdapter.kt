package com.example.jangbogo_2022.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.jangbogo_2022.R
import com.example.jangbogo_2022.databinding.ItemPriceBinding
import com.example.jangbogo_2022.databinding.ItemProductDetailBinding
import com.example.jangbogo_2022.model.ModelDataRecord
import com.example.jangbogo_2022.model.ModelProductDetail
import java.text.DecimalFormat


class ProductDetailViewHolder(val binding: ItemProductDetailBinding) : RecyclerView.ViewHolder(binding.root)

class ProductDetailAdapter(val data: ArrayList<ModelDataRecord>, val isComma: Boolean, val isWonW: Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private lateinit var context: Context
    private lateinit var priceLevelString: ArrayList<String>
    private lateinit var priceLevelColor: ArrayList<Int>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        priceLevelString = arrayListOf<String>(context.resources.getString(R.string.cheap), context.resources.getString(R.string.medium), context.resources.getString(R.string.expensive))
        priceLevelColor = arrayListOf<Int>(ContextCompat.getColor(context, R.color.light_mint),ContextCompat.getColor(context, R.color.mid_yellow),ContextCompat.getColor(context, R.color.dark_orange))
        return ProductDetailViewHolder(ItemProductDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ProductDetailViewHolder).binding
        val d = data[position]
        Log.d("jh", "d in adapter : ${d}")

        binding.tvProductDetailStore.text = d.rStore
        binding.tvProductDetailItemMemo.text = d.rMemo

        val tag = d.rTag ?: 1
        
        binding.tvProductDetailTag.text = priceLevelString[tag] //tag값 0 1 2에 따라 string값 설정. null이라면 1로 설정.
        DrawableCompat.setTint(binding.tvProductDetailTag.background, priceLevelColor[tag]) //color값 설정

        var won = "원"
        if(isWonW){
            won = "￦"
        }

        if(isComma){
            val dec = DecimalFormat("#,###")
            binding.tvProductDetailPrice.text = dec.format(d.rPrice) + won
        }
        else{
            binding.tvProductDetailPrice.text = d.rPrice.toString() + won
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}