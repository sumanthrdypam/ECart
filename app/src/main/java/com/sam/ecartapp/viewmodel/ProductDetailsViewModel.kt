package com.sam.ecartapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sam.ecartapp.model.Product
import com.sam.ecartapp.model.ProductDetailedResponse
import com.sam.ecartapp.model.local.AppDatabase
import com.sam.ecartapp.model.local.Cart
import com.sam.ecartapp.model.remote.ApiService
import com.sam.ecartapp.model.remote.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsViewModel(application: Application): AndroidViewModel(application) {
    private val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    private var _productDetailedResponse = MutableLiveData<ProductDetailedResponse>()
    val productDetailedResponse: LiveData<ProductDetailedResponse> = _productDetailedResponse
    private val appDatabase = AppDatabase.getInstance(getApplication())

    fun getDetailedProductResponse(id: String) {
        apiService.getDetailedProductDetails(id)
            .enqueue(object : Callback<ProductDetailedResponse> {
                override fun onResponse(
                    call: Call<ProductDetailedResponse>,
                    response: Response<ProductDetailedResponse>
                ) {
                    if (response.isSuccessful) {
                        _productDetailedResponse.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ProductDetailedResponse>, t: Throwable) {
                    t.printStackTrace()
                }

            })
    }

    fun onItem(position: String, quantity: Int) {
        val productDetails = _productDetailedResponse.value!!.product
        val product = Product(
            productDetails.averageRating,
            productDetails.categoryId,
            "",
            productDetails.description,
            productDetails.price,
            productDetails.productId,
            "",
            productDetails.productName,
            "",
            ""
        )
        val cart = Cart(position, product, quantity)
        var q = quantity
        if (q > 0) {
            val previousQuantity = appDatabase.cartDao().findCartItemById(cart.productId)
            if (previousQuantity != null && previousQuantity.quantity > 0) {
                q += previousQuantity.quantity
            }
            appDatabase.cartDao().insertCart(Cart(position, product, q))

        } else {
            appDatabase.cartDao().deleteFromCart(cart)
        }
    }

    fun setItem(position: String, quantity: Int) {
        val productDetails = _productDetailedResponse.value!!.product
        val product = Product(
            productDetails.averageRating,
            productDetails.categoryId,
            "",
            productDetails.description,
            productDetails.price,
            productDetails.productId,
            "",
            productDetails.productName,
            "",
            ""
        )
        val cart = Cart(position, product, quantity)
        if(quantity>0){
            appDatabase.cartDao().insertCart(Cart(position, product, quantity))
        }else{
            appDatabase.cartDao().deleteFromCart(cart)
        }

    }
}