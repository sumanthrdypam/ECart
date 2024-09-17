package com.sam.ecartapp.view.productlist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sam.ecartapp.AppConstants
import com.sam.ecartapp.databinding.ProductItemViewBinding
import com.sam.ecartapp.model.Product
import com.sam.ecartapp.model.local.Cart
import com.squareup.picasso.Picasso

class ProductAdapter(val context:Context, val products:List<Product>):Adapter<ProductViewHolder>() {

    lateinit var onItem:(Int,Int)->Unit

    fun setOnItemListener(f:((Int,Int)->Unit)){
        onItem = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItemViewBinding.inflate(inflater,parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder.productItem){
            with(products[position]){
                productTitle.text = productName
                productDiscription.text = description
                productCost.text = "$ ${price}"
                productrating.rating = averageRating.toFloat()
                Picasso.get().load("${AppConstants.BASE_IMAGE_URL}${productImageUrl}").into(productImage)
                var count=0
                btnPlus.setOnClickListener {
                    if(!btnAdd.text.equals("ADD TO CART")){
                        count = btnAdd.text.toString().toInt()
                    }
                    count++
                    btnAdd.setText(count.toString())
                    onItem(position,count)
                }
                btnMinus.setOnClickListener {
                    if(!btnAdd.text.equals("ADD TO CART")){
                        count = btnAdd.text.toString().toInt()
                    }else{
                        return@setOnClickListener
                    }
                    count--
                    btnAdd.setText(count.toString())
                    onItem(position,count)
                    if(count==0){
                        btnAdd.text = "ADD TO CART"
                    }
                }

            }
        }
    }
}
class CartAdapter(val context:Context, val cartList:List<Cart>):Adapter<ProductViewHolder>() {

    lateinit var onItem:(Int,Int)->Unit

    fun setOnItemListener(f:((Int,Int)->Unit)){
        onItem = f
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItemViewBinding.inflate(inflater,parent,false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with(holder.productItem){
            with(cartList[position]){
                val productFromCart = product
                productTitle.text = productFromCart.productName
                productDiscription.text = productFromCart.description
                productCost.text = "$ ${productFromCart.price}"
                productrating.rating = productFromCart.averageRating.toFloat()
                Picasso.get().load("${AppConstants.BASE_IMAGE_URL}${productFromCart.productImageUrl}").into(productImage)
                btnAdd.text = quantity.toString()
                var count=0
                btnPlus.setOnClickListener {
                    if(!btnAdd.text.equals("ADD TO CART")){
                        count = btnAdd.text.toString().toInt()
                    }
                    count++
                    btnAdd.setText(count.toString())
                    onItem(position,count)
                }
                btnMinus.setOnClickListener {
                    if(!btnAdd.text.equals("ADD TO CART")){
                        count = btnAdd.text.toString().toInt()
                    }else{
                        return@setOnClickListener
                    }
                    count--
                    btnAdd.setText(count.toString())
                    onItem(position,count)
                    if(count==0){
                        btnAdd.text = "ADD TO CART"
                    }
                }

            }
        }
    }
}