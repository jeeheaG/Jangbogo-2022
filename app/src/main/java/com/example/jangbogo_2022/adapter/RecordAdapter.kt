package com.example.jangbogo_2022.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jangbogo_2022.databinding.ItemRecordBinding
import com.example.jangbogo_2022.model.ModelDataRecord
import com.example.jangbogo_2022.model.ModelRecordCard
import com.example.jangbogo_2022.network.MyApplication

class RecordViewHolder(val binding: ItemRecordBinding) : RecyclerView.ViewHolder(binding.root)

class RecordAdapter(val data: ArrayList<ModelRecordCard>, val email: String, val itemClick: (ModelRecordCard) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return RecordViewHolder(ItemRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as RecordViewHolder).binding
        val d = data[position]

        binding.tvProductName.text = d.name

        binding.tvPriceCheap.text = d.cheap
        binding.tvPriceMedium.text = d.medium
        binding.tvPriceExpensive.text = d.expensive

//        //binding.tvPriceCheap.text = d.name?.let { callRecordSmallData(it, email, 0) }
//        val tagViewList = arrayListOf<TextView>(binding.tvPriceCheap, binding.tvPriceMedium, binding.tvPriceExpensive)
//        for(i in 0..2){
//            tagViewList[i].text = d.name?.let { callRecordSmallData(it, email, i) }
//        }


        //받아온 doc의 이미지 받아와서 셋
        val imageRef = MyApplication.storage.reference.child("images/product/${d.productDocId}.jpg")
        imageRef.downloadUrl.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Glide.with(context)
                    .load(task.result)
                    .into(binding.ivImage)
            }
        }

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

        //클릭리스너 달기
        binding.clRecordItem.setOnClickListener{
            itemClick(data[position])
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

//
//    private fun callRecordSmallData(pName: String, email: String, tag: Int): String {
//        var result = ""
//        val itemList = mutableListOf<ModelDataRecord>()
//        MyApplication.db.collection("record")
//            .whereEqualTo("pName", pName)
//            .whereEqualTo("email", email)
//            .whereEqualTo("rTag", tag)
////            .orderBy("rPrice") //rPrice 오름차순 정렬. 작은 것부터
//            .limit(1)
//            .get()
//            .addOnSuccessListener { result ->
//                if(result != null && result.size() != 0){
//                    for(document in result){
//                        val item = document.toObject(ModelDataRecord::class.java)
////                    itemList.add(item)
//                        itemList.add(item)
//                    }
//                    Log.d("jh", "recordAdapter callRecordSmall itemList : ${itemList}")
//                }
//            }
//            .addOnFailureListener {
//                Log.d("jh", "recordAdapter callRecordSmall Fail")
//            }
//        if(itemList.size == 0){
//            result = "-"
//        }else{
//            result = itemList[0].rStore ?: "-"
//        }
//        return result
//
//    }



}
