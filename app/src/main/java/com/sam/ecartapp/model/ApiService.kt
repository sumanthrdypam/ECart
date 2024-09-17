package com.sam.ecartapp.model

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
}