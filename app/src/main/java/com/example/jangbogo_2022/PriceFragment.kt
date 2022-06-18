package com.example.jangbogo_2022

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jangbogo_2022.adapter.PriceSearchAdapter
import com.example.jangbogo_2022.databinding.FragmentPriceBinding
import com.example.jangbogo_2022.model.ModelPriceSearch

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
        val binding = FragmentPriceBinding.inflate(inflater, container, false)

        val dummy = arrayListOf<ModelPriceSearch>(
            ModelPriceSearch("뫄뫄 마트마트마트", "아삭사과", "10개", "10000원"),
            ModelPriceSearch("뫄뫄2 마트마트마트", "사과", "20개", "100원"),
            ModelPriceSearch("뫄뫄3 마트마트마트", "아삭아삭사과", "30개", "7000원"),
            ModelPriceSearch("뫄뫄4 마트마트마트", "아삭아삭아삭사과", "40개", "10000원"),
            ModelPriceSearch("뫄뫄5 마트마트마트", "아삭아삭아아아아아삭사과", "50개", "10000원"),
            ModelPriceSearch("뫄뫄6 마트마트마트", "아삭사과", "60개", "10000원"),
            ModelPriceSearch("뫄뫄7 마트마트마트", "아삭사과", "70개", "10000원"),
            ModelPriceSearch("뫄뫄8 마트마트마트", "아삭사과", "80개", "10000원")
        )

        binding.rvPriceSearch.adapter = PriceSearchAdapter(dummy)
        binding.rvPriceSearch.layoutManager = LinearLayoutManager(activity)





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