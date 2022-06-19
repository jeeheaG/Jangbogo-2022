package com.example.jangbogo_2022.network

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: MultiDexApplication() { //Application(),
    companion object{
        ////////////Retrofit
        var networkService: NetworkService

        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init {
            networkService = retrofit.create(NetworkService::class.java)
        }

        ///////////Firebase

        lateinit var auth: FirebaseAuth
        var email: String? = null

        //Firebase, Storage 사용하기 (이미지공유)
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage

        //Firebase로그인
//        //사용자의 이메일이 검증된 이메일인가 체크
//        fun checkAuth(): Boolean{
//            var currentUser = auth.currentUser
//            return currentUser?.let {//currentUser 값이 있으면 그 유저의 이메일 인증 여부 반환
//                email = currentUser.email
//                currentUser.isEmailVerified
//            }?: let { //currentUser 값이 null이면 false 반환
//                false
//            }
//        }

    }

    override fun onCreate() {
        super.onCreate()
        //Firebase로그인
        auth = Firebase.auth

        //카카오 로그인
        KakaoSdk.init(this, "9ec5d029a660fbcaa7fd5538f3134270")


        //TODO : Firebase, Storage 사용하기 (이미지공유)
        //초기화
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }

}