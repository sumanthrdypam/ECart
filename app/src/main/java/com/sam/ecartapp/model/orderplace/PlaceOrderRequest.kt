package com.sam.ecartapp.model.orderplace

import com.google.gson.annotations.SerializedName

data class PlaceOrderRequest(
    @SerializedName("bill_amount")
    val billAmount: Int,
    @SerializedName("delivery_address")
    val deliveryAddress: DeliveryAddress,
    val items: List<Item>,
    @SerializedName("payment_method")
    val paymentMethod: String,
    @SerializedName("user_id")
    val userId: Int
)