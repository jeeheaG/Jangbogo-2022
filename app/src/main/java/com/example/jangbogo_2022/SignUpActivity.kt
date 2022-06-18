package com.example.jangbogo_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jangbogo_2022.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS

            val email = binding.etSignUpEmail.text.toString()
            val password = binding.etSignUpPassword.text.toString()

            //이메일 형식 체크
            if(!emailPattern.matcher(email).matches()){
                Toast.makeText(applicationContext, "이메일 형식에 맞지 않습니다.", Toast.LENGTH_SHORT).show()
//                dialogShow("이메일 형식에 맞지 않습니다.", binding.etSignUpEmail, false)
            }

            //비밀번호
            else if(password.trim().isEmpty()){
                Toast.makeText(applicationContext, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
//                dialogShow("비밀번호를 입력해주세요.", binding.etSignUpPassword, false)
            }

            //비밀번호와 비밀번호 확인 일치 체크
            else if(password != binding.etSignUpPasswordCheck.text.toString()){
                Toast.makeText(applicationContext, "비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//                dialogShow("비밀번호 확인이 일치하지 않습니다.", binding.etSignUpPassword, false)
            }

            //이용약관
            else if(!binding.cbSignUp.isChecked){
                Toast.makeText(applicationContext, "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show()
//                dialogShow("이용약관에 동의해주세요.",null, false)
            }

            //회원가입 요청
            else{
                Toast.makeText(applicationContext, "회원가입을 요청합니다.", Toast.LENGTH_SHORT).show()
                finish()

/*                val body = HashMap<String, String>()
                body["name"] = name
                body["nickname"] = nickname
                body["email"] = email
                body["password"] = password
                body["address"] = address
                body["phoneNumber"] = phoneNumber
                Log.d("signup", "name: ${name}, nickname: ${nickname}, email: ${email}, pw: ${password}, addr: ${address}, phone: ${phoneNumber}")
                callPostSignUp(body)*/

            }
        }


    }
}