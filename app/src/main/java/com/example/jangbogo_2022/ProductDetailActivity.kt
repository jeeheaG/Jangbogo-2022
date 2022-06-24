package com.example.jangbogo_2022

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.jangbogo_2022.adapter.ProductDetailAdapter
import com.example.jangbogo_2022.databinding.ActivityProductDetailBinding
import com.example.jangbogo_2022.model.ModelDataProduct
import com.example.jangbogo_2022.model.ModelDataRecord
import com.example.jangbogo_2022.model.ModelProductDetail
import com.example.jangbogo_2022.model.ModelRecordCard
import com.example.jangbogo_2022.network.MyApplication

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var productDetailAdapter: ProductDetailAdapter
    private var dataRecordList  = arrayListOf<ModelDataRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid = MyApplication.auth.uid ?: ""

//        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name") ?: ""

        //sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isComma = sharedPreferences.getBoolean("comma", true)
        val isWonW = sharedPreferences.getBoolean("won", false)
        val bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF") // MySettingFragment에서 선택된 값을 가져옴!!
        binding.rootProductDetail.setBackgroundColor(Color.parseColor((bgColor)))

        binding.ivBack.setOnClickListener {
            finish()
        }

        //상품 상세 정보 불러오기
        callProductData(name)
        //해당 상품에 대한 기록정보 목록 불러오기
        callRecordListData(name, uid)

        //binding.tvProductDetailTitle.text = name
        //Toast.makeText(this, "상품의 id: ${id}", Toast.LENGTH_SHORT).show()

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("jh", "it.resultCode : ${it.resultCode}")
            if(it.resultCode === android.app.Activity.RESULT_OK){
                callRecordListData(name, uid)
            }
        }
        binding.ivProductDetailAdd.setOnClickListener {
            val recordAddIntent = Intent(this, RecordAddActivity::class.java)
//            recordAddIntent.putExtra("id", id)
            recordAddIntent.putExtra("name", name)
            requestLauncher.launch(recordAddIntent)
        }

        productDetailAdapter = ProductDetailAdapter(dataRecordList, isComma, isWonW)
        binding.rvProductDetail.adapter = productDetailAdapter
        binding.rvProductDetail.layoutManager = LinearLayoutManager(this)

    }

    private fun callRecordListData(pName: String, uid: String) {
        MyApplication.db.collection("record")
            .whereEqualTo("pName", pName)
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { result ->
//                val itemList = mutableListOf<ModelDataRecord>()
                dataRecordList.clear()
                for(document in result){
                    val item = document.toObject(ModelDataRecord::class.java)
//                    itemList.add(item)
                    dataRecordList.add(item)
                }

                Log.d("jh", "dataRecordList : ${dataRecordList}")
                productDetailAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.d("jh", "callProductData Fail")
            }


    }

    private fun callProductData(pName: String) {
        MyApplication.db.collection("product")
            .whereEqualTo("pName", pName)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                val itemList = mutableListOf<ModelDataProduct>()
                for(document in result){
                    val item = document.toObject(ModelDataProduct::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }

                Log.d("jh", "itemList : ${itemList}")

                binding.tvProductDetailTitle.text = itemList[0].pName
                binding.tvProductDetailMemo.text = itemList[0].pMemo

                //받아온 doc의 이미지 받아와서 셋
                val imageRef = MyApplication.storage.reference.child("images/product/${itemList[0].docId}.jpg")
                imageRef.downloadUrl.addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Glide.with(this)
                            .load(task.result)
                            .into(binding.ivProductDetail)
                    }
                }
            }
            .addOnFailureListener {
                Log.d("jh", "callProductData Fail")
            }


    }
}