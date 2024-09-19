package com.sam.ecartapp.view.orderdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.ecartapp.databinding.FragmentOrdersBinding
import com.sam.ecartapp.model.OrderResponse
import com.sam.ecartapp.viewmodel.OrderViewModel


class OrdersFragment : Fragment() {
    private lateinit var binding:FragmentOrdersBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var orderAdapter: OrderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]
        orderViewModel.orderDetails()
        orderViewModel.orderResponse.observe(viewLifecycleOwner){
            setAdapter(it)
        }
    }

    private fun setAdapter(orderResponse: OrderResponse) {
        orderAdapter = OrderAdapter(requireActivity(),orderResponse.orders)
        with(binding.recyclerView){
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}