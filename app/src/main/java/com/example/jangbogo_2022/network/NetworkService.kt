package com.example.jangbogo_2022.network

import com.example.jangbogo_2022.model.ModelDataPrice
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {
    @GET("{key}/json/ListNecessariesPricesService/{startIdx}/{endIdx}/{store}/{product}/{yearMonth}//{address}")
    fun getPriceData(
        @Path("key") key: String,
        @Path("startIdx") startIdx: Int,
        @Path("endIdx") endIdx: Int,
        @Path("store") store: String,
        @Path("product") product: String,
        @Path("yearMonth") yearMonth: String,
//        @Path("address") address: String
    ) : Call<ModelDataPrice>

}