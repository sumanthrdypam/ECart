package com.sam.ecartapp.view.subcategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.sam.ecartapp.databinding.FragmentSubCategoryBinding
import com.sam.ecartapp.model.ApiService
import com.sam.ecartapp.model.RetrofitBuilder
import com.sam.ecartapp.model.SubCategoryListResponse
import com.sam.ecartapp.model.SubCategoryResponse
import com.sam.ecartapp.view.productlist.ProductListFragment
import com.sam.ecartapp.viewmodel.SubCategoryViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubCategoryFragment : Fragment() {
    private lateinit var binding : FragmentSubCategoryBinding
    private lateinit var subCategoryViewModel: SubCategoryViewModel
    val listOfFragments = mutableListOf<ProductListFragment>()

    var subCategoryId=""
    var subCategoryName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subCategoryId = arguments?.getString("CategoryId").toString()
        subCategoryName = arguments?.getString("categoryName").toString()
        subCategoryViewModel = ViewModelProvider(requireActivity())[SubCategoryViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSubCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireActivity(), subCategoryId,Toast.LENGTH_SHORT).show()
        subCategoryViewModel.subCategoryListResponse.observe(viewLifecycleOwner){
            subCategoryListResponse->
            subCategoryViewModel.generateFragments(subCategoryListResponse)
        }
        subCategoryViewModel.getListOfSubCategory(subCategoryId)
        setActionBar()
        subCategoryViewModel.listOfFragment.observe(viewLifecycleOwner){
            fragments->
            onSubCategoryListResponseReceived(subCategoryViewModel.subCategoryListResponse.value!!,fragments)
        }

    }

    private fun setActionBar(){
        (activity as AppCompatActivity?)!!.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                title = ""//nullifying the title
            }
            binding.toolbarTitle.text = subCategoryName
            binding.backButton.setOnClickListener {
                val action = SubCategoryFragmentDirections.actionSubCategoryFragmentToHomeScreen()
                findNavController().navigate(action)
            }
        }
    }

    private fun onSubCategoryListResponseReceived(subCategoryListResponse: SubCategoryListResponse,  listOfFragmentsToRender:MutableList<ProductListFragment>) {
        val subCategoryList = subCategoryListResponse.subcategories
        val adapter = ViewPagerAdapter(requireActivity(),subCategoryList.size, listOfFragmentsToRender)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){
                tab,position->
                tab.text = subCategoryListResponse.subcategories[position].subcategoryName
        }.attach()
    }

}



