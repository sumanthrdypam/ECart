package com.sam.ecartapp.view.subcategory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sam.ecartapp.view.productlist.ProductListFragment

class ViewPagerAdapter(
    fragmentManager: FragmentActivity,
    products1: Int,
    private val products: MutableList<ProductListFragment>
):FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int {
        return products.size
    }

    override fun createFragment(position: Int): Fragment {
        return products[position]
    }
}