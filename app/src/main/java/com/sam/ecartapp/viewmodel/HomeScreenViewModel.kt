package com.sam.ecartapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.ecartapp.model.remote.ApiService
import com.sam.ecartapp.model.CategoryResponse
import com.sam.ecartapp.model.remote.RetrofitBuilder
import com.sam.ecartapp.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreenViewModel:ViewModel() {
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    private val _categoryResponse=MutableLiveData<CategoryResponse>()
    val categoryResponse:LiveData<CategoryResponse> = _categoryResponse
    private val _searchResponse = MutableLiveData<SearchResponse>()
    val searchResponse = _searchResponse


    fun getCategories(){
        apiService.getCategory().enqueue(object : Callback<CategoryResponse>{
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()?.status ==0){
                        _categoryResponse.value = response.body()
                    }else{
                        Log.i("tag","Categories Response Invalid")
                    }
                }else{
                    Log.i("tag","Response of Categories Failed")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.i("tag","Request of Categories Failed")
            }

        })
    }

    fun search(query:String){
        apiService.performSearch(query).enqueue(object:Callback<SearchResponse>{
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if(response.isSuccessful){
                    if(response.body()?.status ==0){
                        _searchResponse.value = response.body()
                    }else{
                        Log.i("tag","Categories Search Invalid")
                    }
                }else{
                    Log.i("tag","Response of Search Failed")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.i("tag","Request of Search Failed")
            }

        })
    }


}