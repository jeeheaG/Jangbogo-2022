package com.example.jangbogo_2022.model

data class ModelDataPrice(
    val ListNecessariesPricesService: ListNecessariesPricesService
)

data class ListNecessariesPricesService(
    val row: MutableList<PriceData>
)

data class PriceData(
    val M_NAME: String, //가게 이름
    val A_NAME: String, //상품 이름
    val A_UNIT: String, //가격의 단위
    val A_PRICE: Int,   //단위당 가격
    val P_YEAR_MONTH: String, //점검 연월 : 년도-월 2022-05
    val P_DATE: String,     //점검 연월일 : 2022-05-07
    val M_GU_NAME: String   //자치구 이름
)
