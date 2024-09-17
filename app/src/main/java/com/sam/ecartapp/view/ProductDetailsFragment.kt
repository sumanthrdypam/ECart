package com.sam.ecartapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sam.ecartapp.R
import com.sam.ecartapp.databinding.FragmentProductDetailsBinding
import com.sam.ecartapp.model.ProductDetailedResponse
import com.sam.ecartapp.viewmodel.ProductDetailsViewModel
import com.squareup.picasso.Picasso

class ProductDetailsFragment : Fragment() {
    private lateinit var binding : FragmentProductDetailsBinding
    private lateinit var viewModel: ProductDetailsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ProductDetailsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.productDetailedResponse.observe(viewLifecycleOwner){
            inflateData(it)
        }
        var productid = "0"
        productid = arguments?.getString("productId").toString()
        viewModel.getDetailedProductResponse(productid)
    }

    private fun inflateData(productDetailedResponse: ProductDetailedResponse) {
        with(binding){
            with(productDetailedResponse){
                productName.text = product.productName
                Picasso.get().load(productDetailedResponse.product.productImageUrl).error(R.drawable.baseline_shopping_cart_24).into(productImage)
                productPrice.text = product.price
                productDescription.text = product.description
                var spec = ""
                if(product.specifications!=null && product.specifications.size>0){
                    product.specifications.forEach {
                        spec += it.toString()
                    }.toString()
                }
                specModelName.text = spec
                var count=0
                addToCartButton.setOnClickListener {
                    btnPlus.setOnClickListener {
                        if(!addToCartButton.text.equals("ADD TO CART")){
                            count = addToCartButton.text.toString().toInt()
                        }
                        count++
                        addToCartButton.setText(count.toString())
                        viewModel.onItem(productDetailedResponse.product.productId,count)
                    }
                    btnMinus.setOnClickListener {
                        if(!addToCartButton.text.equals("ADD TO CART")){
                            count = addToCartButton.text.toString().toInt()
                        }else{
                            return@setOnClickListener
                        }
                        count--
                        addToCartButton.setText(count.toString())
                        viewModel.onItem(productDetailedResponse.product.productId,count)
                        if(count==0){
                            addToCartButton.text = "ADD TO CART"
                        }
                    }
                }
            }
        }

    }



}