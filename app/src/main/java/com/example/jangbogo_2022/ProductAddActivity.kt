package com.example.jangbogo_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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



    }
}