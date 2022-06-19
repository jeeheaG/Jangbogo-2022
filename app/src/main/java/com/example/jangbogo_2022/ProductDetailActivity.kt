package com.example.jangbogo_2022

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jangbogo_2022.adapter.ProductDetailAdapter
import com.example.jangbogo_2022.databinding.ActivityProductDetailBinding
import com.example.jangbogo_2022.model.ModelProductDetail

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isComma = sharedPreferences.getBoolean("comma", true)
        val isWonW = sharedPreferences.getBoolean("won", false)
        val bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF") // MySettingFragment에서 선택된 값을 가져옴!!
        binding.rootProductDetail.setBackgroundColor(Color.parseColor((bgColor)))

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvProductDetailTitle.text = name
        Toast.makeText(this, "상품의 id: ${id}", Toast.LENGTH_SHORT).show()

        binding.ivProductDetailAdd.setOnClickListener {
            val recordAddIntent = Intent(this, RecordAddActivity::class.java)
            recordAddIntent.putExtra("id", id)
            recordAddIntent.putExtra("name", name)
            startActivity(recordAddIntent)
        }

        val dummy = arrayListOf<ModelProductDetail>(
            ModelProductDetail("aa", "와와 마트", "이런이런 메모를 하면된다!", 1, 1000),
            ModelProductDetail("bb", "와와 마트2", "이런이런 메모를 하면된다!", 0, 100000),
            ModelProductDetail("cc", "와와 마트3", "이런이런 메모를 하면된다!", 2, 1990),
            ModelProductDetail("dd", "와와 마트4", "이런이런 메모를 하면된다!", 1, 79990),
            ModelProductDetail("ee", "와와 마트5", "이런이런 메모를 하면된다!", 0, 1000),
            ModelProductDetail("ff", "와와 마트6", "이런이런 메모를 하면된다!", 2, 1000),
        )

        binding.rvProductDetail.adapter = ProductDetailAdapter(dummy, isComma, isWonW)
        binding.rvProductDetail.layoutManager = LinearLayoutManager(this)

    }
}