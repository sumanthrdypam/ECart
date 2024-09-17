package com.sam.ecartapp.model

import android.app.Activity
import android.graphics.Color
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar


    fun showSnackbar(view: View, msg:String){
        val sanckbar = Snackbar.make(view,msg, Snackbar.LENGTH_SHORT)
        sanckbar.show()
        sanckbar.setTextColor(Color.parseColor("#FF5722"))
    }
