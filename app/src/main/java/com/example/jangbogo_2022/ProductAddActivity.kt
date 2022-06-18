package com.example.jangbogo_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jangbogo_2022.databinding.ActivityProductAddBinding

class ProductAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnProductAdd.setOnClickListener {
        //TODO : 서버에 상품추가 요청
            Toast.makeText(this, "상품을 추가합니다.", Toast.LENGTH_SHORT).show()
            finish()
        }

    }
}