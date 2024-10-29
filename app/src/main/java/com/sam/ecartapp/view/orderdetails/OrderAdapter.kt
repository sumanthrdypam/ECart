package com.sam.ecartapp.view.orderdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sam.ecartapp.databinding.OrderItemListBinding
import com.sam.ecartapp.model.Order

class OrderAdapter(val context: Context, val list:List<Order>) : Adapter<OrderAdapter.OrderViewHolder>() {


    inner class OrderViewHolder(val binding:OrderItemListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val binding = OrderItemListBinding.inflate(layoutInflater,parent,false)
        return OrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = list[position]

        with(holder.binding) {
            orderId.text = "Order #${order.orderId}"
            orderDate.text = order.orderDate
            addressTitle.text = order.addressTitle
            address.text = order.address
            billAmount.text = "₹${order.billAmount}"
            paymentMethod.text = order.paymentMethod
            orderStatus.text = order.orderStatus

            when (order.orderStatus) {
                "RECEIVED" -> holder.binding.orderStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.holo_green_dark
                    )
                )

                "PENDING" -> holder.binding.orderStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.holo_orange_dark
                    )
                )

                "CANCELLED" -> holder.binding.orderStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.holo_red_dark
                    )
                )

                else -> holder.binding.orderStatus.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.black
                    )
                )
            }
        }
    }
}