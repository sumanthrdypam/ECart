package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class AddAddressRequest(
    @SerializedName("user_id")
    val userId: Int,
    val title: String,
    val address: String,
)