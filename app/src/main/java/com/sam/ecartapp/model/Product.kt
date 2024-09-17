package com.sam.ecartapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Product(
    @SerializedName("average_rating")
    val averageRating: String,
    @SerializedName("category_id")
    val categoryId: String,
    @SerializedName("category_name")
    val categoryName: String,
    val description: String,
    val price: String,

    @PrimaryKey(autoGenerate = false)
    @SerializedName("product_id")
    val productId: String,
    @SerializedName("product_image_url")
    val productImageUrl: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("sub_category_id")
    val subCategoryId: String,
    @SerializedName("subcategory_name")
    val subCategoryName: String
)