package com.example.jangbogo_2022

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.jangbogo_2022.databinding.ActivityMainBinding

private const val TAG_PRICE = "price"
private const val TAG_RECORD = "record"
private const val TAG_SETTING = "setting"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val category = manager.findFragmentByTag(TAG_PRICE)
        val home = manager.findFragmentByTag(TAG_RECORD)
        val myPage = manager.findFragmentByTag(TAG_SETTING)

        //모든 프래그먼트 hide
        if(category!=null){
            ft.hide(category)
        }
        if(home!=null){
            ft.hide(home)
        }
        if(myPage!=null){
            ft.hide(myPage)
        }

        //선택한 항목에 따라 그에 맞는 프래그먼트만 show
        if(tag == TAG_PRICE){
            if(category!=null){
                ft.show(category)
            }
        }
        else if(tag == TAG_RECORD){
            if(home!=null){
                ft.show(home)
            }
        }
        else if(tag == TAG_SETTING){
            if(myPage!=null){
                ft.show(myPage)
            }
        }

        //마무리
        ft.commitAllowingStateLoss()
        //ft.commit()

    }//seFragment함수 끝
}