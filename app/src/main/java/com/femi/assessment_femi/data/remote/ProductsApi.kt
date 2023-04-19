package com.femi.assessment_femi.data.remote

import com.femi.assessment_femi.data.product.ProductItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {

    @GET(/* Nothing to see here, Move along :) */ "/api/v1/products.json")
    suspend fun getProducts(): List<ProductItem>
}