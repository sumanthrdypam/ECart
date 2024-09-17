package com.sam.ecartapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sam.ecartapp.databinding.FragmentRegisterUserBinding
import com.sam.ecartapp.model.RegisterUserRequest
import com.sam.ecartapp.model.RegisterUserResponse
import com.sam.ecartapp.model.showSnackbar
import com.sam.ecartapp.viewmodel.RegisterUserViewModel


class RegisterUserFragment : Fragment() {
    private lateinit var binding:FragmentRegisterUserBinding
    private lateinit var registerUserViewModel: RegisterUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerUserViewModel = ViewModelProvider(this)[RegisterUserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerUserViewModel.registerUserResponse.observe(viewLifecycleOwner){
            response ->
            if (response != null) {
                validateAndNavigateToLoginFragment(response)
            }
        }
        with(binding){
            btnRegister.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                registerUser()
            }
            btnLogin.setOnClickListener {
                val action = RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun registerUser() {
        with(binding){
            val fullName:String = etFullName.text.toString()
            val email = etEmailId.text.toString()
            val mobileNumber = etMobileNo.text.toString()
            val password = etPassword.text.toString()
            if(fullName.isEmpty() || email.isEmpty() || mobileNumber.isEmpty() || password.isEmpty()){
                showSnackbar(binding.root, "Enter All Details")
                progressBar.visibility = View.GONE
                return
            }
            val registerUserRequest = RegisterUserRequest(email,fullName,mobileNumber,password)
            registerUserViewModel.registerUser(registerUserRequest)
        }
    }

    private fun validateAndNavigateToLoginFragment(registerUserResponse: RegisterUserResponse){
        binding.progressBar.visibility = View.GONE
        if(registerUserResponse.status == 0){
            showSnackbar(binding.root,"Registration Success")
            val action = RegisterUserFragmentDirections.actionRegisterUserFragmentToLoginFragment()
            findNavController().navigate(action)
        }else{
            showSnackbar(binding.root,"Registration Failed")
        }
    }

}