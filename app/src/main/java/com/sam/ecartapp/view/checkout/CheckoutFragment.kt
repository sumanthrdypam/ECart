package com.sam.ecartapp.view.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.sam.ecartapp.databinding.FragmentCheckoutBinding
import com.sam.ecartapp.view.CartFragment
import com.sam.ecartapp.view.subcategory.SubCategoryFragmentDirections
import com.sam.ecartapp.viewmodel.CheckoutViewModel


class CheckoutFragment : Fragment() {
    lateinit var binding: FragmentCheckoutBinding
    private lateinit var checkoutViewModel: CheckoutViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkoutViewModel = ViewModelProvider(requireActivity())[CheckoutViewModel::class.java]
        setActionBar()
        setViewPagerAndTabLayoutSetup()
    }

    private fun setActionBar(){
        (activity as AppCompatActivity?)!!.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.let {
                title = ""
            }
            binding.backButton.setOnClickListener {
                val action = CheckoutFragmentDirections.actionCheckoutFragmentToCartFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setViewPagerAndTabLayoutSetup() {
        val listOfTitles = listOf(CART_FRAGMENT, DELIVERY_FRAGMENT, PAYMENT_FRAGMENT, SUMMARY_FRAGMENT)//change to constants
        val listOfFragments = listOf<Fragment>(CheckoutCartFragment())
        val viewPagerAdapter = CheckoutViewPagerAdapter(requireActivity(), listOfFragments)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab,position->
            tab.text = listOfTitles[position]
        }.attach()
    }

    companion object{
        const val CART_FRAGMENT = "Cart Items"
        const val DELIVERY_FRAGMENT = "Delivery"
        const val PAYMENT_FRAGMENT = "Payment"
        const val SUMMARY_FRAGMENT = "Summary"
    }


}