package com.example.jangbogo_2022

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.preference.PreferenceManager
import com.example.jangbogo_2022.databinding.ActivityMainBinding

private const val TAG_PRICE = "price"
private const val TAG_RECORD = "record"
private const val TAG_SETTING = "setting"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        var bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF") // MySettingFragment에서 선택된 값을 가져옴!!
        binding.llMainActivity.setBackgroundColor(Color.parseColor((bgColor)))

        //맨 처음 보여줄 프래그먼트 설정
        setFragment(TAG_RECORD, RecordFragment())
        binding.mainNavi.selectedItemId = R.id.fragmentRecord

        //네비 항목 클릭 시 프래그먼트 변경하는 함수 호출
        // TODO : setOnNavigationItemSelectedListener가 deprecated되어서 대체했는데 setOnItemReselectedListener 는 뭐가 다른 거지?
        binding.mainNavi.setOnItemSelectedListener { item->
            Log.d("jh", "itemId : ${item.itemId}")
            when(item.itemId){
                R.id.fragmentPrice -> setFragment(TAG_PRICE, PriceFragment())
                R.id.fragmentRecord -> setFragment(TAG_RECORD, RecordFragment())
                R.id.fragmentSetting -> setFragment(TAG_SETTING, SettingFragment())
            }
            true
        }

/*        //프래그먼트 설정
        val resultFragmentId = intent.getIntExtra("selectFragmentId", 0)
        binding.mainNavi.selectedItemId = resultFragmentId*/

    }

    //프래그먼트 컨트롤 함수
    private fun setFragment(tag: String, fragment: Fragment){
        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()

        //트랜잭션에 tag로 전달된 fragment가 없을 경우 add
        if(manager.findFragmentByTag(tag) == null){
            ft.add(R.id.mainNaviFragmentContainer, fragment, tag)
        }

        //작업이 수월하도록 manager에 add되어있는 fragment들을 변수로 할당해둠
        val price = manager.findFragmentByTag(TAG_PRICE)
        val record = manager.findFragmentByTag(TAG_RECORD)
        val setting = manager.findFragmentByTag(TAG_SETTING)

        //모든 프래그먼트 hide
        if(price!=null){
            ft.hide(price)
        }
        if(record!=null){
            ft.hide(record)
        }
        if(setting!=null){
            ft.hide(setting)
        }

        //선택한 항목에 따라 그에 맞는 프래그먼트만 show
        if(tag == TAG_PRICE){
            if(price!=null){
                ft.show(price)
            }
        }
        else if(tag == TAG_RECORD){
            if(record!=null){
                ft.show(record)
            }
        }
        else if(tag == TAG_SETTING){
            if(setting!=null){
                ft.show(setting)
            }
        }

        //마무리
        ft.commitAllowingStateLoss()
        //ft.commit()

    }//seFragment함수 끝

    override fun onResume() {
        super.onResume()
       // 배경색 변경
        val bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF")
        binding.llMainActivity.setBackgroundColor(Color.parseColor((bgColor)))

        val manager: FragmentManager = supportFragmentManager
        val ft: FragmentTransaction = manager.beginTransaction()
        manager.findFragmentByTag(TAG_PRICE)?.let { ft.remove(it) }
        ft.commit()
    }
}