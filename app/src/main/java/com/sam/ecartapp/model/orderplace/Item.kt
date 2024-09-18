package com.sam.ecartapp.model.orderplace

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("product_id")
    val productId: Int,
    val quantity: Int,
    @SerializedName("unit_price")
    val unitPrice: Int
)