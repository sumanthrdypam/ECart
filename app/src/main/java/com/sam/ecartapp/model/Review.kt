package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("full_name")
    val fullName: String,
    val rating: String,
    val review: String,
    @SerializedName("review_date")
    val reviewDate: String,
    @SerializedName("review_id")
    val reviewId: String,
    @SerializedName("review_title")
    val reviewTitle: String,
    @SerializedName("user_id")
    val userId: String
)