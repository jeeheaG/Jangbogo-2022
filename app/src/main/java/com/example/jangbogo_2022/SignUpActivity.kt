package com.example.jangbogo_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jangbogo_2022.databinding.ActivitySignUpBinding
import com.example.jangbogo_2022.network.MyApplication

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

            //비밀번호 6자리
            else if(password.trim().length < 6){
                Toast.makeText(applicationContext, "비밀번호는 6자리 이상 설정해주세요.", Toast.LENGTH_SHORT).show()
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
                callFirebaseSignUp(email, password)
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

    private fun callFirebaseSignUp(email: String, password: String) {
        Toast.makeText(applicationContext, "이메일 회원가입을 요청합니다.", Toast.LENGTH_SHORT).show()
        Log.d("jh", "auth : ${MyApplication.auth.currentUser}")
        Log.d("jh", "uid : ${MyApplication.auth.uid}")


        MyApplication.auth.createUserWithEmailAndPassword(email, password) //Firebase.auth의 함수 사용!
            .addOnCompleteListener(this) { task ->
                Log.d("jh", "auth.createUserWithEmailAndPassword의 OnCompleteListener")
                //요청이 완료되면 사용자가 입력했던 EditText내용들을 지운다.
                binding.etSignUpEmail.text.clear()
                binding.etSignUpPassword.text.clear()

                //요청이 성공했는지 실패했는지 따라 작업한다.
                if(task.isSuccessful){ //회원가입 1단계 성공
                    Log.d("jh", "task.isSuccessful")
/*                    MyApplication.auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener { sendTask ->
                            if(sendTask.isSuccessful){  //메일 발송 성공 - 회원가입 최종 성공
                                Log.d("jh", "sendTask.isSuccessful")
                                Toast.makeText(baseContext, "회원 가입 성공!! 메일을 확인해주세요.", Toast.LENGTH_SHORT).show()

                            }
                            else{ //메일 발송 실패
                                Log.d("jh", "fail sendTask.isSuccessful")
                                Toast.makeText(baseContext, "메일 발송 실패", Toast.LENGTH_SHORT).show()
                            }
                        }*/
                }
                else{ //회원가입 실패
                    Log.d("jh", "fail task.isSuccessful")
                    Toast.makeText(baseContext, "회원 가입 실패", Toast.LENGTH_SHORT).show() //TODO : baseContext 를 넣음!
                }
            }
            .addOnFailureListener {
                Log.d("jh", "auth.createUserWithEmailAndPassword의 OnFailureListener")
                it.printStackTrace()
            }
            .addOnCanceledListener {
                Log.d("jh", "auth.createUserWithEmailAndPassword의 addOnCanceledListener")

            }

    }
}