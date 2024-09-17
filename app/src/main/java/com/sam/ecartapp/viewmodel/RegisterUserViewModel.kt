package com.sam.ecartapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.ecartapp.model.ApiService
import com.sam.ecartapp.model.RegisterUserRequest
import com.sam.ecartapp.model.RegisterUserResponse
import com.sam.ecartapp.model.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserViewModel: ViewModel() {
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    private val _registerUserResponse=MutableLiveData<RegisterUserResponse?>()
    val registerUserResponse: LiveData<RegisterUserResponse?> = _registerUserResponse

    fun registerUser(registerUserRequest: RegisterUserRequest){
        apiService.registerUser(registerUserRequest).enqueue(object: Callback<RegisterUserResponse>{
            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
            ) {
                if(response.isSuccessful){
                    _registerUserResponse.value = response.body()
                }else{
                    Log.i("tag","Register Failed")
                }
            }

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                Log.i("tag", "Register Request Failed")
            }

        })
    }

    fun clearLoginResponse() {
        _registerUserResponse.value = null
    }
}