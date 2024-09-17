package com.sam.ecartapp.model

data class ProductDetailedResponse(
    val message: String,
    val product: ProductDetails,
    val status: Int
)