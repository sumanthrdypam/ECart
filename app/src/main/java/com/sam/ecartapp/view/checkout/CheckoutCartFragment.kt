package com.sam.ecartapp.view.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.ecartapp.R
import com.sam.ecartapp.databinding.FragmentCartBinding
import com.sam.ecartapp.databinding.FragmentCheckoutBinding
import com.sam.ecartapp.databinding.FragmentCheckoutCartBinding
import com.sam.ecartapp.model.local.AppDatabase
import com.sam.ecartapp.model.local.Cart
import com.sam.ecartapp.view.CartFragmentDirections
import com.sam.ecartapp.view.MainActivity
import com.sam.ecartapp.view.productlist.CartAdapter
import com.sam.ecartapp.viewmodel.CartViewModel

class CheckoutCartFragment : Fragment() {
    private lateinit var binding : FragmentCheckoutCartBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartItems = AppDatabase.getInstance(requireActivity()).cartDao().getAllProductsAsList()
        setProductAdapter(cartItems)
        calculateTotal(cartItems)
        binding.btnNext.setOnClickListener {

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
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }


}