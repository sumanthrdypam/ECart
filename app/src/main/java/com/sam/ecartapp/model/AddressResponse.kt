package com.sam.ecartapp.model

data class AddressResponse(
    val addresses: List<Address>,
    val message: String,
    val status: Int
)