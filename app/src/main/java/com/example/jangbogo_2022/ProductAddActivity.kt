package com.example.jangbogo_2022

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.example.jangbogo_2022.databinding.ActivityProductAddBinding

class ProductAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductAddBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF") // MySettingFragment에서 선택된 값을 가져옴!!
        binding.rootProductAdd.setBackgroundColor(Color.parseColor((bgColor)))

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