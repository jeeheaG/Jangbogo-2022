package com.example.jangbogo_2022

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.jangbogo_2022.databinding.ActivityRecordAddBinding

class RecordAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordAddBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF") // MySettingFragment에서 선택된 값을 가져옴!!
        binding.rootRecordAdd.setBackgroundColor(Color.parseColor((bgColor)))

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