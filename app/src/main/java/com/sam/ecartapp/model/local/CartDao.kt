package com.sam.ecartapp.model.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(cart: Cart)

    @Delete
    fun deleteFromCart(cart:Cart)

    @Query("SELECT * FROM Cart")
    fun getAllProducts():LiveData<List<Cart>>

    @Query("SELECT * FROM Cart")
    fun getAllProductsAsList():List<Cart>

    @Query("SELECT * FROM Cart WHERE productId =:id")
    fun findCartItemById(id:String) : Cart

    @Query("DELETE FROM Cart")
    fun deleteAllFromCart()
}