package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class RegisterUserRequest(
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("mobile_no")
    val mobileNumber: String,
    val password: String
)