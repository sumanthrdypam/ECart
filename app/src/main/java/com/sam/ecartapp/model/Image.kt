package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("display_order")
    val displayOrder: String,
    val image: String
)