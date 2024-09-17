package com.sam.ecartapp.model.remote

import com.sam.ecartapp.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private lateinit var retrofit: Retrofit
    fun getRetrofit():Retrofit{

        if(!RetrofitBuilder::retrofit.isInitialized) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstants.BASE_API_URL)
                .client(okHttpClient)
                .build()
        }
        return retrofit
    }

}