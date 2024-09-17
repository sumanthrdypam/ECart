package com.sam.ecartapp.model

data class LoginResponse(
    val message: String,
    val status: Int,
    val user: User
)