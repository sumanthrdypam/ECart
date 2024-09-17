package com.sam.ecartapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sam.ecartapp.model.User

object SharedPreferenceManager {
    const val IS_LOGIN="LOGIN"
    private const val PREF_NAME:String = "LOGIN DETAILS"
    private const val USER:String = "USER"

    private lateinit var  sharedPreferences : SharedPreferences

    fun init(context: Context){
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveString( key:String,  value:String) = sharedPreferences.edit().putString(key,value).commit()

    fun getString(key:String) = sharedPreferences.getString(key,"")?:""


    fun saveBoolean(key:String, value:Boolean) = sharedPreferences.edit().putBoolean(key,value).commit()


    fun getBoolean(key:String) = sharedPreferences.getBoolean(key,false)


    fun removeKey(key:String):Boolean = sharedPreferences.edit().remove(key).commit()

    fun saveUser(user: User){
        sharedPreferences.edit().putString(USER, Gson().toJson(user)).apply()
    }

    fun clearUser(){
        sharedPreferences.edit().remove(USER).apply()
    }

    fun getUser():User{
        val stringUser = sharedPreferences.getString(USER,null)
        if(stringUser!=null){
            return Gson().fromJson(stringUser, User::class.java)
        }
        return User("User Not Found","","NULL","")
    }


    fun clearData() = sharedPreferences.edit().clear().apply()



}