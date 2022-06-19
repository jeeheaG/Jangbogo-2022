package com.example.jangbogo_2022

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jangbogo_2022.databinding.ActivityLoginBinding
import com.example.jangbogo_2022.network.MyApplication
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        //카카오 로그인을 위한 앱의 키 해시 구하기 : 내 키해시 Vl7eN5KttTWQrlvbUTsT/I4FbfE=
        val keyHash = Utility.getKeyHash(this)
        Log.d("jh", keyHash)*/

        //이메일 로그인
        binding.btnLoginLogin.setOnClickListener {
            val emailPattern = android.util.Patterns.EMAIL_ADDRESS

            val email = binding.etLoginEmail.text.toString()
            val password = binding.etLoginPassword.text.toString()

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

            else{ //모든 확인이 끝나면 로그인 요청
                //TODO : 로그인 ... 함수로 뺄 것
                var email = binding.etLoginEmail.text.toString()
                var password = binding.etLoginPassword.text.toString()
                callFirebaseEmailLogin(email, password)
            }
        }

        //이메일 회원가입
        binding.btnLoginSignUp.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }

        //카카오 로그인
        binding.btnLoginKakaoLogIn.setOnClickListener {
            // 토큰 정보 보기 //TODO - ???
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Log.e("jh", "토큰 정보 보기 실패", error)
                }
                else if (tokenInfo != null) {
                    Log.i("jh", "토큰 정보 보기 성공" )
//                    finish()
                }
            }

            //콜백 함수 만들기
            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("jh", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("jh", "카카오계정으로 로그인 성공 ${token.accessToken}")

                    // 사용자 정보 요청 (기본)
                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("jh", "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.i("jh", "사용자 정보 요청 성공 ${user.kakaoAccount?.email}")
                            Toast.makeText(applicationContext, "${user.kakaoAccount?.email}님 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                            //사용자로부터 동의를 받아야하는 항목들을 담을 변수
                            var scopes = mutableListOf<String>()

                            //여기서 바로 이메일 정보가 받아와 졌다면 변수에 저장하고 종료
                            if(user.kakaoAccount?.email != null){
                                MyApplication.email = user.kakaoAccount?.email
                                loginCompleteMoveMain()
                                //finish()

                            }
                            //바로 이메일 정보가 받아와지지 않았고(null이고), emailNeedsAgreement==true이면
                            // 추가적인 동의를 받아야 한다!
                            else if(user.kakaoAccount?.emailNeedsAgreement == true){
                                Log.i("jh", "사용자에게 추가 동의 필요")
                                scopes.add("account_email") //동의를 받을 항목을 담음
                                //loginWithNewScopes : 동의 요청과 함께 로그인 재시도. token은 성공 시 정보가 옴(?)
                                UserApiClient.instance.loginWithNewScopes(this, scopes){token, error ->
                                    if(error != null){ //error
                                        Log.e("jh", "추가 동의 실패", error)
                                    }
                                    else{ //error없음. 로그인 성공인 듯. 사용자 정보 재요청
                                        UserApiClient.instance.me { user, error ->
                                            if(error != null){ //재요청 error
                                                Log.e("jh", "사용자 정보 재요청 실패", error)
                                            }
                                            else if(user != null){ //재요청 성공! 변수에 저장
                                                MyApplication.email = user.kakaoAccount?.email.toString()
                                                Toast.makeText(applicationContext, "${user.kakaoAccount?.email}님 동의되었습니다.", Toast.LENGTH_SHORT).show()
                                                loginCompleteMoveMain()
                                                //finish()
                                            }
                                        }
                                    }
                                }
                            }
                            //이메일 정보도 없고 추가 동의도 구할 수 없음. 사용자 정보 가져올 없음
                            else{
                                Log.e("jh", "이메일 획득 불가", error)
                            }
                        }
                    }
                }
            }

            //카카오톡이 설치되어있다면
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback) //카카오톡 앱으로 로그인
            }
            else{ //설치되어있지 않다면
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback) //카카오계정 로그인
            }
        }

        binding.tvLoginAsk.setOnClickListener { 
            //TODO : 개발자 문의 페이지로
        }
        
        
    }

    private fun loginCompleteMoveMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun callFirebaseEmailLogin(email: String, password: String) {
        Toast.makeText(this, "이메일 로그인을 요청합니다.", Toast.LENGTH_SHORT).show()

        MyApplication.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.etLoginEmail.text.clear()
                binding.etLoginPassword.text.clear()

                if(task.isSuccessful){//로그인 성공
                    MyApplication.email = email
                    Toast.makeText(baseContext, "${MyApplication.auth.currentUser?.email}님 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("jh","auth. currentUser : ${MyApplication.auth.currentUser}")
                    Log.d("jh","auth. currentUser : ${MyApplication.auth.currentUser?.email}")
                    Log.d("jh", "uid : ${MyApplication.auth.uid}")

                    //사용자가 이메일 인증된 상태인가 아닌가 따라서 처리
/*                    if(MyApplication.checkAuth()){//사용자가 이메일 인증된 상태일 때
                        MyApplication.email = email
                        //finish() //화면 종료
                    }
                    else{
                        Toast.makeText(baseContext, "이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                    }*/
                }
                else{//로그인 실패
                    Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()

                }
            }
        //TODO : 덕지덕지 코드...
        MyApplication.email = email
        Toast.makeText(baseContext, "${MyApplication.auth.currentUser?.email}님 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
        loginCompleteMoveMain()
    }


}