package com.sam.ecartapp.model.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sam.ecartapp.model.Product

@Entity
data class Cart(
    @PrimaryKey val productId:String,
    @Embedded(prefix = "product")
    val product:Product,
    var quantity:Int
)
