package com.sam.ecartapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.ecartapp.model.remote.ApiService
import com.sam.ecartapp.model.LoginRequest
import com.sam.ecartapp.model.LoginResponse
import com.sam.ecartapp.model.remote.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    private val _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse : MutableLiveData<LoginResponse?> = _loginResponse

    fun loginUser(loginRequest: LoginRequest){
        apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    Log.i("tag", "Request Sent")
                    if(response.body()!=null) {
                        val body = response.body()
                        _loginResponse.value = body!!
                    }
                }else{
                    Log.i("tag", "Couldn't Sent the Request")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.i("tag", "Failed request")
                t.printStackTrace()
                loginUser(loginRequest) // trying to again call the same request until we connect to the server
            }

        })
    }

    fun clearLoginResponse() {
        _loginResponse.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("tag","cleared the  loginviewmodel data")
    }

}