package com.sam.ecartapp.model

data class OrderResponse(
    val message: String,
    val orders: List<Order>,
    val status: Int

)