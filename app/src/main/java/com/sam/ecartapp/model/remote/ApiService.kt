package com.sam.ecartapp.model.remote

import com.sam.ecartapp.model.CategoryResponse
import com.sam.ecartapp.model.LoginRequest
import com.sam.ecartapp.model.LoginResponse
import com.sam.ecartapp.model.ProductDetailedResponse
import com.sam.ecartapp.model.RegisterUserRequest
import com.sam.ecartapp.model.RegisterUserResponse
import com.sam.ecartapp.model.SearchResponse
import com.sam.ecartapp.model.SubCategoryListResponse
import com.sam.ecartapp.model.SubCategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("content-type: application/json")
    @POST("User/register")
    fun registerUser(@Body registerUserRequest: RegisterUserRequest): Call<RegisterUserResponse>

    @Headers("content-type: application/json")
    @POST("User/auth")
    fun loginUser(@Body loginRequest: LoginRequest) : Call<LoginResponse>

    @GET("Category")
    fun getCategory(): Call<CategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    fun getSubCategory(@Path("sub_category_id") subCategoryId:String):Call<SubCategoryResponse>

    @GET("SubCategory")
    fun getListOfSubCategories(@Query("category_id") categoryId:String) : Call<SubCategoryListResponse>

    @GET("Product/search")
    fun performSearch(@Query("query") query: String):Call<SearchResponse>

    @GET("Product/details/{product_id}")
    fun getDetailedProductDetails(@Path("product_id") id:String) : Call<ProductDetailedResponse>
}