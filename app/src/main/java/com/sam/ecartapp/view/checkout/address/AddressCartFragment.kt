package com.sam.ecartapp.view.checkout.address

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sam.ecartapp.databinding.AddressBottomSheetBinding
import com.sam.ecartapp.databinding.FragmentAddressCartBinding
import com.sam.ecartapp.model.Address
import com.sam.ecartapp.model.showSnackbar
import com.sam.ecartapp.viewmodel.AddressCartViewModel

class AddressCartFragment : Fragment() {
    private lateinit var addressCartBinding: FragmentAddressCartBinding
    private lateinit var addressCartViewModel: AddressCartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addressCartViewModel = ViewModelProvider(requireActivity())[AddressCartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addressCartBinding = FragmentAddressCartBinding.inflate(layoutInflater)
        return addressCartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        addressCartBinding.addAddress.setOnClickListener { initBottomSheet() }
        addressCartViewModel.addAddressResponse.observe(viewLifecycleOwner){
            showSnackbar(addressCartBinding.root, it.message)
        }
        addressCartViewModel.addressList.observe(viewLifecycleOwner){
            displayAddressOptions(it)
        }
        addressCartViewModel.getAddress()
    }

    private fun initBottomSheet() {
        val bottomSheet = BottomSheetDialog(requireActivity())
        val btmSheet = AddressBottomSheetBinding.inflate(requireActivity().layoutInflater)
        bottomSheet.setContentView(btmSheet.root)
        bottomSheet.show()
        btmSheet.saveButton.setOnClickListener {
            val title = btmSheet.tvTitle.text.toString()
            val address = btmSheet.tvAddress.text.toString()
            if (title.isNotEmpty() && address.isNotEmpty()) {
                addressCartViewModel.addAddress(title,address)
                bottomSheet.dismiss()
  /*              val radioGroup = addressCartBinding.addressRadioGroup
                val radioButton = RadioButton(requireContext()).apply {
                    text = "${title}\n${address}"
                    id = View.generateViewId()
                    tag = address.addressId
                }
                radioGroup.addView(radioButton)*/

            } else {
                showSnackbar(btmSheet.root,"Please enter both title and address")
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayAddressOptions(addressList: List<Address>) {
        val radioGroup = addressCartBinding.addressRadioGroup
        for (address in addressList) {
            val radioButton = RadioButton(requireContext()).apply {
                text = "${address.title}\n${address.address}"
                id = View.generateViewId()
                tag = address
            }
            radioGroup.addView(radioButton)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = group.findViewById<RadioButton>(checkedId)
            val selectedAddress = selectedRadioButton.tag as Address
            addressCartViewModel.setSelectedAddress(selectedAddress)
        }
    }

}