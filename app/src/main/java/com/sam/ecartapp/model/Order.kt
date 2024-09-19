package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class Order(
    val address: String,
    @SerializedName("address_title")
    val addressTitle: String,
    @SerializedName("bill_amount")
    val billAmount: String,
    @SerializedName("order_date")
    val orderDate: String,
    @SerializedName("order_id")
    val orderId: String,
    @SerializedName("order_status")
    val orderStatus: String,
    @SerializedName("payment_method")
    val paymentMethod: String
)