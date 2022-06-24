package com.example.jangbogo_2022

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.isDigitsOnly
import androidx.preference.PreferenceManager
import com.example.jangbogo_2022.databinding.ActivityRecordAddBinding
import com.example.jangbogo_2022.network.MyApplication
import com.example.jangbogo_2022.util.dateToString
import java.util.*

class RecordAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecordAddBinding
    lateinit var sharedPreferences: SharedPreferences
    private var selectedTag = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF") // MySettingFragment에서 선택된 값을 가져옴!!
        binding.rootRecordAdd.setBackgroundColor(Color.parseColor((bgColor)))

//        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")

        binding.tvRecordAddTagCheap.setOnClickListener {
            setTag(0)
        }

        binding.tvRecordAddTagMedium.setOnClickListener {
            setTag(1)
        }

        binding.tvRecordAddTagExpensive.setOnClickListener {
            setTag(2)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvRecordAddSubtitleName.text = name


        binding.btnRecordAdd.setOnClickListener {
            if(binding.etRecordAddPrice.text.isNotEmpty() && binding.etRecordAddName.text.isNotEmpty()){
                if(isNumber(binding.etRecordAddPrice.text.toString())){
                    //TODO : 서버 기록 추가 요청..
                    Toast.makeText(this, "기록을 추가합니다. 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show()
                    saveRecord(name)
                }else{
                    Toast.makeText(this, "가격은 숫자로만 작성해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "가격과 가게명을 반드시 작성해주세요.", Toast.LENGTH_SHORT).show()
            }
            //finish()
        }


    }

    fun isNumber(s: String?): Boolean {
        return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
    }

    private fun saveRecord(pName: String?) {
        val data = mapOf(
            "uid" to MyApplication.auth.uid,
            "email" to MyApplication.email,
            "pName" to pName,
            "rStore" to binding.etRecordAddName.text.toString(),
            "rPrice" to binding.etRecordAddPrice.text.toString().toInt(),
            "rTag" to selectedTag,
            "rMemo" to binding.etRecordAddMemo.text.toString(),
            "rDate" to dateToString(Date())
        )

        MyApplication.db.collection("record")
            .add(data)
            .addOnSuccessListener {
                // 화면 종료
                val intent = Intent(this, ProductDetailActivity::class.java)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            .addOnFailureListener {
                Log.d("jh", "record doc save error ............")
            }

    }

    private fun setTag(tag: Int){
        if(selectedTag != tag){
            selectedTag = tag
            val tagViewList = arrayListOf<TextView>(binding.tvRecordAddTagCheap, binding.tvRecordAddTagMedium, binding.tvRecordAddTagExpensive)
            for (i in 0..2){
                if(tag==i){
                    tagViewList[tag].background = ResourcesCompat.getDrawable(resources, R.drawable.button_round, null)
                }
                else{
                    tagViewList[i].background = ResourcesCompat.getDrawable(resources, R.drawable.button_round_stroke, null)
                }
            }
        }
    }

}