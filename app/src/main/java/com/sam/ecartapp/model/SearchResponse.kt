package com.sam.ecartapp.model

data class SearchResponse(
    val message: String,
    val products: List<Product>,
    val status: Int
)