package com.sam.ecartapp.model

data class SubCategoryListResponse(
    val message: String,
    val status: Int,
    val subcategories: List<Subcategory>
)