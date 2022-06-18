package com.example.jangbogo_2022

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jangbogo_2022.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.btnLoginLogin.setOnClickListener {
            if(false){
                //TODO : 입력란 확인 ..
                
            }
            else{ //모든 확인이 끝나면 로그인 요청
                //TODO : 로그인 ... 함수로 뺄 것

                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                finish()
            }
        }
        
        binding.btnLoginSignUp.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }

        binding.tvLoginAsk.setOnClickListener { 
            //TODO : 개발자 문의 페이지로
        }
        
        
    }
}