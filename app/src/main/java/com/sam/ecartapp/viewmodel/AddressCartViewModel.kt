package com.sam.ecartapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sam.ecartapp.SharedPreferenceManager
import com.sam.ecartapp.model.AddAddressRequest
import com.sam.ecartapp.model.AddAddressResponse
import com.sam.ecartapp.model.Address
import com.sam.ecartapp.model.AddressResponse
import com.sam.ecartapp.model.User
import com.sam.ecartapp.model.local.AppDatabase
import com.sam.ecartapp.model.orderplace.DeliveryAddress
import com.sam.ecartapp.model.orderplace.Item
import com.sam.ecartapp.model.orderplace.PlaceOrderRequest
import com.sam.ecartapp.model.orderplace.PlaceOrderResponse
import com.sam.ecartapp.model.remote.ApiService
import com.sam.ecartapp.model.remote.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressCartViewModel(application: Application):AndroidViewModel(application) {
    var user: User = SharedPreferenceManager.getUser()
    val apiService = RetrofitBuilder.getRetrofit().create(ApiService::class.java)
    private val _addAddressResponse=MutableLiveData<AddAddressResponse>()
    val addAddressResponse:LiveData<AddAddressResponse> = _addAddressResponse

    private val _addressList=MutableLiveData<List<Address>>()
    val addressList = _addressList
     var selectedAddressOne=MutableLiveData<Address>()
    var paymentModeOne=MutableLiveData<String>("COD")
    lateinit var totalBill :String

    private val appDatabase = AppDatabase.getInstance(getApplication())

    fun addAddress(title: String, address: String) {
        val addAddressRequest = AddAddressRequest(user.userId.toInt(), title, address)
        apiService.addAddress(addAddressRequest).enqueue(object :Callback<AddAddressResponse>{

            override fun onResponse(
                call: Call<AddAddressResponse>,
                response: Response<AddAddressResponse>
            ) {
                _addAddressResponse.value = response.body()
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getAddress(){
        apiService.getAddress(user.userId).enqueue(object:Callback<AddressResponse>{

            override fun onResponse(
                call: Call<AddressResponse>,
                response: Response<AddressResponse>
            ) {
                _addressList.value = response.body()?.addresses
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun setSelectedAddress(address: Address){
        selectedAddressOne.value = address
    }

    fun setPaymentMode(s: String) {
        paymentModeOne.value = s
    }

    fun getCartItems() = appDatabase.cartDao().getAllProductsAsList()

    fun calculateTotal(): String {
        val list = getCartItems()
        var total = 0.0
        list.forEach {
            val quantity = it.quantity
            val price = it.product.price.toDouble()
            total += (quantity * price)
        }
        totalBill = total.toString()
        return total.toString()
    }

    fun placeOrder(){
        val listItems = getCartItems()
        val ans = mutableListOf<Item>()
        for(item in listItems){
            ans.add( Item(item.product.productId.toInt(),item.quantity,item.product.price.toInt()))
        }
        val placeOrderRequest = PlaceOrderRequest(
            billAmount = totalBill.toFloat().toInt(),
            deliveryAddress = DeliveryAddress(selectedAddressOne.value!!.address,selectedAddressOne.value!!.title),
            paymentMethod = paymentModeOne.value!!,
            userId = SharedPreferenceManager.getUser().userId.toInt(),
            items = ans
        )
        apiService.placeOrder(placeOrderRequest).enqueue(object :Callback<PlaceOrderResponse>{
            override fun onResponse(
                call: Call<PlaceOrderResponse>,
                response: Response<PlaceOrderResponse>
            ) {

            }

            override fun onFailure(call: Call<PlaceOrderResponse>, t: Throwable) {

            }

        })
    }

}