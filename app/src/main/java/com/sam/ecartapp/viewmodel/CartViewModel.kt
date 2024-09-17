package com.sam.ecartapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sam.ecartapp.model.local.AppDatabase
import com.sam.ecartapp.model.local.Cart

class CartViewModel(application: Application):AndroidViewModel(application) {
    private var appDatabase: AppDatabase = AppDatabase.getInstance(getApplication())

    val latestCart: LiveData<List<Cart>> = appDatabase.cartDao().getAllProducts()


    fun addItem(cartItem: Cart){
        val cart = appDatabase.cartDao().findCartItemById(cartItem.productId)
        appDatabase.cartDao().insertCart(cartItem)
    }

    fun updateItem(cartItem:Cart){
        val cart = appDatabase.cartDao().findCartItemById(cartItem.productId)
        if(cart!=null && cart.quantity>0){
            cartItem.quantity +=cart.quantity
        }
        appDatabase.cartDao().insertCart(cartItem)
    }

    fun deleteItem(cartItem:Cart){
        appDatabase.cartDao().deleteFromCart(cartItem)
    }

}