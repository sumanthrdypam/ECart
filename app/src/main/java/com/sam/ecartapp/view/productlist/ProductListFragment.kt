package com.sam.ecartapp.view.productlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.ecartapp.databinding.FragmentProductListBinding
import com.sam.ecartapp.model.Product
import com.sam.ecartapp.model.local.Cart
import com.sam.ecartapp.viewmodel.CartViewModel


class ProductListFragment(val products:List<Product>) : Fragment() {
    private lateinit var binding: FragmentProductListBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter(products)
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    private fun setAdapter(products: List<Product>) {
        val adapter = ProductAdapter(requireActivity(),products)
        binding.productsRecyclerView.adapter = adapter
        binding.productsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter.setOnItemListener{
            productIndex,quantity->
                if(quantity>0){

                    cartViewModel.addItem(Cart(products[productIndex].productId,products[productIndex],quantity))
                }else{
                    cartViewModel.deleteItem(Cart(products[productIndex].productId,products[productIndex],quantity))
                }
        }
    }





}