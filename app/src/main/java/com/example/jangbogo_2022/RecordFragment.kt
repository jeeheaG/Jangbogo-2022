package com.example.jangbogo_2022

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jangbogo_2022.adapter.RecordAdapter
import com.example.jangbogo_2022.databinding.FragmentRecordBinding
import com.example.jangbogo_2022.model.ModelDataProduct
import com.example.jangbogo_2022.model.ModelDataRecord
import com.example.jangbogo_2022.model.ModelRecordCard
import com.example.jangbogo_2022.network.MyApplication
import com.example.jangbogo_2022.util.myCheckPermission

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var dataRecordCard  = arrayListOf<ModelRecordCard>()
//    private var dataProduct = arrayListOf<ModelDataProduct>()
    private lateinit var recordAdapter: RecordAdapter

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
        val binding = FragmentRecordBinding.inflate(inflater, container, false)

        val email = MyApplication.email ?:""
        Log.d("jh", "Record Fragment email : ${email}")

        //현 유저의 상품정보 불러오기 (목록)
        callProductListData(email)

        myCheckPermission(activity as AppCompatActivity) //TODO : ?
        // 상품 추가 화면 실행 후 돌아왔을 때 리사이클러 뷰 데이터 다시 요청
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("jh", "it.resultCode : ${it.resultCode}")
            if(it.resultCode === android.app.Activity.RESULT_OK){
                Log.d("jh", "복귀 후 launcher 실행 call")
                callProductListData(email)
            }
        }
        binding.fbtnProductAdd.setOnClickListener {
            val productAddIntent = Intent(activity, ProductAddActivity::class.java)
            requestLauncher.launch(productAddIntent)
        }

//        val dummy = arrayListOf<ModelRecordCard>(
//            ModelRecordCard("a", "사과", "https://bit.ly/3yAt3za", "동네 슈퍼1", "하나로마트", "노원 신세계"),
//            ModelRecordCard("b", "사과2", "https://bit.ly/3yAt3za", "동네 슈퍼", "하나로마트2", "노원 신세계"),
//            ModelRecordCard("c", "사과3", "https://bit.ly/3yAt3za", "동네 슈퍼", "하나로마트", "노원 신세계3"),
//            ModelRecordCard("d", "사과4", "https://bit.ly/3yAt3za", "동네 슈퍼", "하나로마트", "노원 신세계3"),
//            ModelRecordCard("e", "사과5", "https://bit.ly/3yAt3za", "동네 슈퍼", "하나로마트", "노원 신세계3"),
//
//            )

        val requestDetailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            Log.d("jh", "it.resultCode : ${it.resultCode}")
            callProductListData(email)
        }

        recordAdapter = RecordAdapter(dataRecordCard, email){ ModelRecordCard ->
//            Toast.makeText(activity, "ItemClicked: ${ModelRecordCard.name}", Toast.LENGTH_SHORT).show()
            val productDetailIntent = Intent(activity, ProductDetailActivity::class.java)
//            productDetailIntent.putExtra("id", ModelRecordCard.productDocId)
            productDetailIntent.putExtra("name", ModelRecordCard.name)
            requestDetailLauncher.launch(productDetailIntent)
        }

        binding.rvRecord.adapter = recordAdapter
        binding.rvRecord.layoutManager = LinearLayoutManager(activity)





        // Inflate the layout for this fragment
        return binding.root
    }

    private fun callProductListData(email: String?) {
        MyApplication.db.collection("product")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                val itemList = arrayListOf<ModelDataProduct>()
                dataRecordCard.clear()
                for(document in result){
                    val item = document.toObject(ModelDataProduct::class.java)
                    item.docId = document.id
                    itemList.add(item)
//
//                    for(i in 0..2){
//                        callRecordSmallData(item.pName?:"", email?:"", i, item.docId?:"")
//                    }
                    callRecordSmallData0(item.pName?:"", email?:"",  item.docId?:"")

//                    dataRecordCard.add(ModelRecordCard(item.docId,item.pName, "", "-", "-", "-"))
                }

                Log.d("jh", "productList : ${itemList}")
                //dataProduct = itemList
                //recordAdapter.notifyDataSetChanged()


            }
            .addOnFailureListener {
                Log.d("jh", "callProductData Fail")
            }

    }


    private fun callRecordSmallData0(pName: String, email: String, docId: String) {
        val tag = 0
        var r = ""
        val itemList = mutableListOf<ModelDataRecord>()
        MyApplication.db.collection("record")
            .whereEqualTo("pName", pName)
            .whereEqualTo("email", email)
            .whereEqualTo("rTag", tag)
//            .orderBy("rPrice") //rPrice 오름차순 정렬. 작은 것부터
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if(result != null && result.size() != 0){
                    for(document in result){
                        val item = document.toObject(ModelDataRecord::class.java)
//                    itemList.add(item)
                        itemList.add(item)
                    }
                    Log.d("jh", "recordAdapter callRecordSmall itemList : ${itemList}")
                }
                if(itemList.size == 0){
                    r = "-"
                }else{
                    r = itemList[0].rStore ?: "-"
                }

                Log.d("jh", "smallRecord0 itemList : ${itemList}")
                callRecordSmallData1(pName, email, docId, r)


            }
            .addOnFailureListener {
                Log.d("jh", "recordAdapter callRecordSmall Fail")
            }
    }


    private fun callRecordSmallData1(pName: String, email: String, docId: String, cheap: String) {
        val tag = 1
        var r = ""
        val itemList = mutableListOf<ModelDataRecord>()
        MyApplication.db.collection("record")
            .whereEqualTo("pName", pName)
            .whereEqualTo("email", email)
            .whereEqualTo("rTag", tag)
//            .orderBy("rPrice") //rPrice 오름차순 정렬. 작은 것부터
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if(result != null && result.size() != 0){
                    for(document in result){
                        val item = document.toObject(ModelDataRecord::class.java)
//                    itemList.add(item)
                        itemList.add(item)
                    }
                    Log.d("jh", "recordAdapter callRecordSmall itemList : ${itemList}")
                }
                if(itemList.size == 0){
                    r = "-"
                }else{
                    r = itemList[0].rStore ?: "-"
                }

                Log.d("jh", "smallRecord1 itemList : ${itemList}")
                callRecordSmallData2(pName, email, docId, cheap, r)
            }
            .addOnFailureListener {
                Log.d("jh", "recordAdapter callRecordSmall Fail")
            }
    }


    private fun callRecordSmallData2(pName: String, email: String, docId: String, cheap: String, medium: String) {
        val tag = 2
        var r = ""
        val itemList = mutableListOf<ModelDataRecord>()
        MyApplication.db.collection("record")
            .whereEqualTo("pName", pName)
            .whereEqualTo("email", email)
            .whereEqualTo("rTag", tag)
//            .orderBy("rPrice") //rPrice 오름차순 정렬. 작은 것부터
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if(result != null && result.size() != 0){
                    for(document in result){
                        val item = document.toObject(ModelDataRecord::class.java)
//                    itemList.add(item)
                        itemList.add(item)
                    }
                    Log.d("jh", "recordAdapter callRecordSmall itemList : ${itemList}")
                }
                if(itemList.size == 0){
                    r = "-"
                }else{
                    r = itemList[0].rStore ?: "-"
                }

                Log.d("jh", "smallRecord3 itemList : ${itemList}")
                dataRecordCard.add(ModelRecordCard(docId, pName, "", cheap, medium, r))
                recordAdapter.notifyDataSetChanged()

            }
            .addOnFailureListener {
                Log.d("jh", "recordAdapter callRecordSmall Fail")
            }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}