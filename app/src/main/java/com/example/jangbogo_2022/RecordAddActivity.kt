package com.example.jangbogo_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jangbogo_2022.databinding.ActivityRecordAddBinding

class RecordAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvRecordAddSubtitleName.text = name


        binding.btnRecordAdd.setOnClickListener {
            //TODO : 서버 기록 추가 요청..
            finish()
        }

    }
}