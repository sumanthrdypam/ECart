package com.sam.ecartapp.view.checkout

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sam.ecartapp.databinding.FragmentAddressCartBinding
import com.sam.ecartapp.databinding.ProductToOrderItemBinding
import com.sam.ecartapp.model.local.Cart

class OrderConfirmationAdapter(val context: Context, val list:List<Cart>) :Adapter<OrderConfirmationAdapter.OrderViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductToOrderItemBinding.inflate(layoutInflater,parent,false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val cart = list[position]
        with(holder.item){
            productName.text = cart.product.productName
            productPrice.text = "Unit Price $ ${cart.product.price}"
            productQuantity.text = "Quantity ${cart.quantity}"
            productAmount.text = ((cart.product.price.toFloat()) * (cart.quantity.toInt())).toString()
        }
    }

    inner class OrderViewHolder(val item:ProductToOrderItemBinding):ViewHolder(item.root)

}