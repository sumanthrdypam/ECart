package com.sam.ecartapp.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.ecartapp.databinding.FragmentHomeScreenBinding
import com.sam.ecartapp.model.CategoryResponse
import com.sam.ecartapp.model.Product
import com.sam.ecartapp.model.local.Cart
import com.sam.ecartapp.view.MainActivity
import com.sam.ecartapp.view.productlist.ProductAdapter
import com.sam.ecartapp.viewmodel.CartViewModel
import com.sam.ecartapp.viewmodel.HomeScreenViewModel

class HomeScreen : Fragment() {
    private lateinit var binding : FragmentHomeScreenBinding
    private lateinit var homeScreenViewModel: HomeScreenViewModel
    private lateinit var adapter : CategoryAdapter
    private lateinit var categoryResposeHolder: CategoryResponse
    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeScreenViewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
        cartViewModel = ViewModelProvider(requireActivity())[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeScreenViewModel.categoryResponse.observe(viewLifecycleOwner){
            categoryResponse->
                setAdapter(categoryResponse)
                categoryResposeHolder=categoryResponse
        }
        homeScreenViewModel.searchResponse.observe(viewLifecycleOwner){
            searchResponse->
            setProductAdapter(searchResponse.products)
        }
        homeScreenViewModel.getCategories()
        setActionBar() //actionbar setting logic
        initSearch()
    }

    private fun initSearch() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    performSearch(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
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
            binding.searchBtn.setOnClickListener {
                if(binding.searchView.visibility ==View.VISIBLE){
                    binding.searchView.visibility = View.GONE
                    setAdapter(categoryResposeHolder)
                }else{
                    binding.searchView.visibility = View.VISIBLE
                    binding.searchView.isSubmitButtonEnabled = true
                }
            }
        }
    }

    private fun setAdapter(categoryResponse: CategoryResponse) {
        adapter = CategoryAdapter(requireActivity(), categoryResponse.categories)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this.context,2)
        adapter.setNavigator{ categoryId,categoryName->
            val action = HomeScreenDirections.actionHomeScreenToSubCategoryFragment(categoryId,categoryName)
            findNavController().navigate(action)
        }
    }

    private fun performSearch(query: String){
        homeScreenViewModel.search(query)
    }

    private fun setProductAdapter(products: List<Product>) {
        val productAdapter = ProductAdapter(requireActivity(), products)
        productAdapter.setOnItemListener {
                productIndex, quantity ->
            if(quantity>=0) {
                cartViewModel.addItem(
                    Cart(
                        products[productIndex].productId,
                        products[productIndex],
                        quantity
                    )
                )
            }else{
                cartViewModel.deleteItem(Cart(
                    products[productIndex].productId,
                    products[productIndex],
                    quantity
                ))
            }

        }
        binding.recyclerView.adapter = productAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

}