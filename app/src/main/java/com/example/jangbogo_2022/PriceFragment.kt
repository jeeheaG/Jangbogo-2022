package com.example.jangbogo_2022

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jangbogo_2022.adapter.PriceSearchAdapter
import com.example.jangbogo_2022.databinding.FragmentPriceBinding
import com.example.jangbogo_2022.model.ModelDataPrice
import com.example.jangbogo_2022.model.ModelPriceSearch
import com.example.jangbogo_2022.model.PriceData
import com.example.jangbogo_2022.network.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PriceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PriceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentPriceBinding
    private var param1: String? = null
    private var param2: String? = null
    private var priceData: ArrayList<ModelPriceSearch> = arrayListOf<ModelPriceSearch>()
    private lateinit var priceAdapter: PriceSearchAdapter
    private var sharedPreferences: SharedPreferences? = null

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
        binding = FragmentPriceBinding.inflate(inflater, container, false)

        //sharedPreferences
        sharedPreferences = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val isComma = sharedPreferences?.getBoolean("comma", true) ?: true
        val isWonW = sharedPreferences?.getBoolean("won", false) ?: false
        val isKeyword = sharedPreferences?.getString("keywordSave", "") ?: ""

        priceAdapter = PriceSearchAdapter(priceData, isComma, isWonW)

        var store = isKeyword
        var product = ""
        var yearMonth = "2022-05"

        callPriceData(store, product, yearMonth)

        binding.etPriceStore.setText(isKeyword)

        var thisYear = 2022
        val yearItems: ArrayList<String> = arrayListOf("-")
        for (i in 0..9) {
            yearItems.add(thisYear.toString())
            thisYear -= 1
        }
        val monthItems: ArrayList<String> = arrayListOf("-")
        for (i in 1..12) {
            if(i<10){
                monthItems.add("0${i}")
            }
            else{
                monthItems.add("${i}")
            }
        }
        Log.d("jh", "yearItems : ${yearItems}," +
                "monthItems : ${monthItems}")

        val yearAdapter = activity?.let { ArrayAdapter(it, R.layout.item_spinner_price, yearItems) }
        val monthAdapter = activity?.let { ArrayAdapter(it, R.layout.item_spinner_price, monthItems) }
        binding.spPriceYear.adapter = yearAdapter
        binding.spPriceMonth.adapter = monthAdapter
        binding.spPriceYear.prompt = "물가 점검 연도를 선택하세요."
        binding.spPriceMonth.prompt = "물가 점검 월을 선택하세요."


//
//        binding.btnPriceKeywordSave.setOnClickListener {
//            Toast.makeText(activity, "현재 검색어가 저장되었습니다.", Toast.LENGTH_SHORT).show()
//
//        }

        binding.btnPriceSearch.setOnClickListener {
            store = binding.etPriceStore.text.toString()
            product = binding.etPriceProduct.text.toString()
            yearMonth = binding.spPriceYear.selectedItem.toString() + "-" + binding.spPriceMonth.selectedItem.toString()

            callPriceData(store, product, yearMonth)
        }

/*        binding.spPriceYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }*/

//        val dummy = arrayListOf<ModelPriceSearch>(
//            ModelPriceSearch("뫄뫄 마트마트마트", "아삭사과", "10개", 10000),
//            ModelPriceSearch("뫄뫄2 마트마트마트", "사과", "20개", 100),
//            ModelPriceSearch("뫄뫄3 마트마트마트", "아삭아삭사과", "30개", 7000),
//            ModelPriceSearch("뫄뫄4 마트마트마트", "아삭아삭아삭사과", "40개", 10000),
//            ModelPriceSearch("뫄뫄5 마트마트마트", "아삭아삭아아아아아삭사과", "50개", 10000),
//            ModelPriceSearch("뫄뫄6 마트마트마트", "아삭사과", "60개", 10000),
//            ModelPriceSearch("뫄뫄7 마트마트마트", "아삭사과", "70개", 10000),
//            ModelPriceSearch("뫄뫄8 마트마트마트", "아삭사과", "80개", 10000)
//        )

        binding.rvPriceSearch.adapter = priceAdapter
        binding.rvPriceSearch.layoutManager = LinearLayoutManager(activity)

        // Inflate the layout for this fragment
        return binding.root
    }
/*

    override fun onResume() {
        super.onResume()
        sharedPreferences = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val isComma = sharedPreferences?.getBoolean("comma", true) ?: true
        Log.d("jh", "onResume, isComma : ${isComma}")

    }

    override fun onPause() {
        super.onPause()
        sharedPreferences = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val isComma = sharedPreferences?.getBoolean("comma", true) ?: true
        Log.d("jh", "onPause, isComma : ${isComma}")
    }

    override fun onStop() {
        super.onStop()
        //sharedPreferences
        sharedPreferences = activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        val isComma = sharedPreferences?.getBoolean("comma", true) ?: true
        val isWonW = sharedPreferences?.getBoolean("won", false) ?: false
        val isKeyword = sharedPreferences?.getString("keyword", "") ?: ""
        Log.d("jh", "onStop, isComma : ${isComma}")

        priceAdapter = PriceSearchAdapter(priceData, isComma, isWonW)
//        priceAdapter.notifyDataSetChanged()

        binding.etPriceStore.setText(isKeyword)
    }
*/

    private fun callPriceData(store: String, product: String, yearMonth: String) {
        val call: Call<ModelDataPrice>? = MyApplication.networkService.getPriceData(
            "54425375706a65653332716f625968",
            1,
            100,
            store,
            product,
            yearMonth
        )

        call?.enqueue(object : Callback<ModelDataPrice>{
            override fun onResponse(
                call: Call<ModelDataPrice>,
                response: Response<ModelDataPrice>
            ) {
                if(response.isSuccessful){
                    Log.d("jh", "success~~ response : ${response}")
                    Log.d("jh", "success~~ response : ${response.body()?.ListNecessariesPricesService?.row}")
                    priceData.clear()
                    val data = response.body()?.ListNecessariesPricesService?.row
                    if (data != null) {
                        for (d in data){
                            val item = ModelPriceSearch(d.P_DATE, d.M_NAME, d.A_NAME, d.A_UNIT, d.A_PRICE)
                            priceData.add(item)
                        }
                    }
                    priceAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<ModelDataPrice>, t: Throwable) {
                Log.d("jh", "failure~~~")
            }

        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PriceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PriceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}