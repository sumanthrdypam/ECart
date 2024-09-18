package com.sam.ecartapp.view.checkout.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.sam.ecartapp.R
import com.sam.ecartapp.databinding.FragmentPaymentBinding
import com.sam.ecartapp.viewmodel.AddressCartViewModel

class PaymentFragment : Fragment() {
    private lateinit var paymentBinding: FragmentPaymentBinding
    private lateinit var addressCartViewModel: AddressCartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        paymentBinding = FragmentPaymentBinding.inflate(layoutInflater)
        addressCartViewModel = ViewModelProvider(requireActivity())[AddressCartViewModel::class.java]
        return paymentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paymentBinding.paymentRadioGroup.setOnCheckedChangeListener() { group, checkedId ->
                when(checkedId){
                    R.id.radio_cod -> addressCartViewModel.setPaymentMode("COD")
                    R.id.radio_ib -> addressCartViewModel.setPaymentMode("Internet Banking")
                    R.id.radio_card -> addressCartViewModel.setPaymentMode("Card")
                    R.id.radio_paypal -> addressCartViewModel.setPaymentMode("Paypal")
                }
        }
        paymentBinding.btnNext.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
            viewPager.currentItem = 3
        }
    }



}