package com.example.jangbogo_2022

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.jangbogo_2022.databinding.FragmentSettingBinding
import com.example.jangbogo_2022.network.MyApplication
import com.kakao.sdk.user.UserApiClient

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingBinding.inflate(inflater, container, false)

        //사용자 설정
        binding.tvSettingOption.setOnClickListener {
            val mySettingIntent = Intent(activity, MySettingActivity::class.java)
            startActivity(mySettingIntent)
//            activity?.finish()
        }


        //로그아웃 버튼 클릭
        binding.tvSettingLogOut.setOnClickListener {
            //이메일 로그인 상태라면
            if(MyApplication.auth.currentUser != null){
                Log.d("jh", "로그인되었던 email : ${MyApplication.email}")
                Log.d("jh", "로그인되었던 uid : ${MyApplication.auth.uid}")

                //Firebase 로그아웃
                MyApplication.auth.signOut()
                MyApplication.email = null
                Log.d("jh", "uid : ${MyApplication.auth.uid}")
                Toast.makeText(activity?.applicationContext, "이메일 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            }

            //카카오 로그인 상태라면
            UserApiClient.instance.me { user, error ->
                if (user != null){
                    //kakao 로그아웃
                    UserApiClient.instance.logout { error ->
                        if(error != null){
                            Toast.makeText(activity, "카카오 로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            Log.d("jh", "카카오 로그아웃 실패")
                        }else{
                            Toast.makeText(activity, "카카오 로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
                            Log.d("jh", "카카오 로그아웃 성공")
                        }
                    }
                }
            }

            val loginIntent = Intent(activity, LoginActivity::class.java)
            startActivity(loginIntent)
            activity?.finish()
        }





        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}