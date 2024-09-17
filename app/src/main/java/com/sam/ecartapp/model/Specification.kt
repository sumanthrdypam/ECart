package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class Specification(
    @SerializedName("display_order")
    val displayOrder: String,
    val specification: String,
    @SerializedName("specification_id")
    val specificationId: String,
    val title: String
)