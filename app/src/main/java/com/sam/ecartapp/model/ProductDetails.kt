package com.sam.ecartapp.model

import com.google.gson.annotations.SerializedName

data class ProductDetails(
    @SerializedName("average_rating")
    val averageRating: String,
    @SerializedName("category_id")
    val categoryId: String,
    val description: String,
    val images: List<Image>,
    @SerializedName("is_active")
    val isActive: String,
    val price: String,
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_image_url")
    val productImageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    val reviews: List<Review>,
    @SerializedName("")
    val specifications: List<Specification>,
    @SerializedName("sub_category_id")
    val subCategoryId: String
)