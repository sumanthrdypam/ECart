package com.sam.ecartapp.view.checkout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CheckoutViewPagerAdapter(fragmentManager: FragmentActivity, private val products: List<Fragment>): FragmentStateAdapter(fragmentManager) {
    override fun getItemCount() = products.size

    override fun createFragment(position: Int) = products[position]
}