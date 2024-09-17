package com.sam.ecartapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.ecartapp.databinding.FragmentCartBinding
import com.sam.ecartapp.model.local.AppDatabase
import com.sam.ecartapp.model.local.Cart
import com.sam.ecartapp.view.productlist.CartAdapter
import com.sam.ecartapp.viewmodel.CartViewModel

class CartFragment : Fragment() {
    private lateinit var binding : FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()
        val cartItems = AppDatabase.getInstance(requireActivity()).cartDao().getAllProductsAsList()
        setProductAdapter(cartItems)
        calculateTotal(cartItems)
    }

    private fun setActionBar(){
        (activity as AppCompatActivity?)!!.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                title = ""//nullifying the title
            }
            val activity = requireActivity() as MainActivity
            binding.hamburger.setOnClickListener {
                activity.toggleDrawer()
            }
        }
    }

    private fun calculateTotal(cartItems: List<Cart>) {
        var total =0.0
        cartItems.forEach { total+=(it.product.price.toDouble()*it.quantity) }
        "$ $total".also { binding.tvSum.text = it }
    }

    private fun setProductAdapter(cart: List<Cart>?) {
        val productAdapter = CartAdapter(requireActivity(), cart!!)
        productAdapter.setOnItemListener {
                productIndex, quantity ->
            if(quantity>0) {
                val cartItem = cart.get(productIndex)
                cartItem.quantity = quantity
                cartViewModel.addItem(cartItem)
            }else{
                cartViewModel.deleteItem(cart[productIndex])
            }

        }
        binding.cartRecyclerView.adapter = productAdapter
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this.context)
    }

}