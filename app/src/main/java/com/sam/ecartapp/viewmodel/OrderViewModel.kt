package com.sam.ecartapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sam.ecartapp.SharedPreferenceManager
import com.sam.ecartapp.model.OrderResponse
import com.sam.ecartapp.model.User
import com.sam.ecartapp.model.remote.ApiService
import com.sam.ecartapp.model.remote.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel(application: Application):AndroidViewModel(application) {
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    private val user:User = SharedPreferenceManager.getUser()
    private val _orderResponse=MutableLiveData<OrderResponse>()
    val orderResponse:LiveData<OrderResponse> = _orderResponse

    fun orderDetails(){
        apiService.getOrders(user.userId).enqueue(object: Callback<OrderResponse>{
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if(response.isSuccessful){
                    _orderResponse.value = response.body()
                }else{
                   Log.i("tag",response.body().toString())
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}