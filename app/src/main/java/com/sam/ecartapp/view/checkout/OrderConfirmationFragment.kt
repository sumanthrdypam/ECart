package com.sam.ecartapp.view.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sam.ecartapp.R
import com.sam.ecartapp.databinding.FragmentOrderConfirmationBinding
import com.sam.ecartapp.model.local.Cart
import com.sam.ecartapp.viewmodel.AddressCartViewModel


class OrderConfirmationFragment : Fragment() {
    private lateinit var orderConfirmationBinding: FragmentOrderConfirmationBinding
    private lateinit var addressCartViewModel: AddressCartViewModel
    private lateinit var adapter: OrderConfirmationAdapter
    private lateinit var cartItems : List<Cart>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressCartViewModel = ViewModelProvider(requireActivity())[AddressCartViewModel::class.java]
        cartItems = addressCartViewModel.getCartItems()
        adapter = OrderConfirmationAdapter(requireContext(),cartItems)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        orderConfirmationBinding = FragmentOrderConfirmationBinding.inflate(layoutInflater)
        return orderConfirmationBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        with(orderConfirmationBinding){
            tvTotalbill.text = addressCartViewModel.calculateTotal()
            addressCartViewModel.selectedAddressOne.observe(viewLifecycleOwner) {
                tvAddressTitle.text = addressCartViewModel.selectedAddressOne.value?.title
                tvAddressDetail.text = addressCartViewModel.selectedAddressOne.value?.address
            }
            addressCartViewModel.paymentModeOne.observe(viewLifecycleOwner){
                tvPayment.text = addressCartViewModel.paymentModeOne.value
            }
            confirmOrder.setOnClickListener {
                addressCartViewModel.placeOrder()
            }
        }
    }

    private fun setAdapter(){
        orderConfirmationBinding.rv.adapter = adapter
        orderConfirmationBinding.rv.layoutManager = LinearLayoutManager(requireActivity())
    }
}