package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class Address(
    val address: String,
    @SerializedName("address_id")
    val addressId: String,
    val title: String
)