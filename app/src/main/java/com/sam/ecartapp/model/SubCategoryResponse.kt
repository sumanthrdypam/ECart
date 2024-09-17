package com.sam.ecartapp.model

data class SubCategoryResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)