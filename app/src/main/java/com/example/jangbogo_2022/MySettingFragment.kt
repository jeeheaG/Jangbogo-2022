package com.example.jangbogo_2022

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.*

/**
 * A simple [Fragment] subclass.
 * Use the [MySettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MySettingFragment : PreferenceFragmentCompat() { //TODO : Setting으로 사용할 프래그먼트임
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey) //settings.xml 파일과 연결해줌

        //TODO : settings.xml 파일 속 요소들을 id값으로 가져올 수 있음
//        val idPreference: EditTextPreference? = findPreference("id")
//        idPreference?.title = "ID 변경"
//        //idPreference?.summary = "ID를 변경할 수 있습니다."


//        val commaPreference: SwitchPreference? = findPreference("comma")
//        commaPreference?.isChecked

        //사용자가 선택한 값이 summary에 출력되게 함
        //idPreference?.summaryProvider = EditTextPreference.SimpleSummaryProvider.getInstance()

//        //사용자가 선택한 값을 약간 변형해서 summary에 출력되게 함
//        idPreference?.summaryProvider = Preference.SummaryProvider<EditTextPreference> {
//                preference ->
//            val text = preference.text
//            if(TextUtils.isEmpty(text)){
//                "ID 설정이 되지 않았습니다."
//            }
//            else{
//                "설정된 ID는 ${text}입니다."
//            }
//        }

        val colorPreference: ListPreference? = findPreference("tagColor")
        colorPreference?.summaryProvider = ListPreference.SimpleSummaryProvider.getInstance()
    }

}