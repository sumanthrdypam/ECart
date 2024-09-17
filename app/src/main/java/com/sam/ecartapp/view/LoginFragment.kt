package com.sam.ecartapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sam.ecartapp.AppConstants
import com.sam.ecartapp.SharedPreferenceManager
import com.sam.ecartapp.databinding.FragmentLoginBinding
import com.sam.ecartapp.model.LoginRequest
import com.sam.ecartapp.model.LoginResponse
import com.sam.ecartapp.model.showSnackbar
import com.sam.ecartapp.viewmodel.LoginViewModel


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        if(SharedPreferenceManager.getBoolean(AppConstants.IS_LOGIN)){
            navigateToHomeScreen()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterUserFragment())
            }
            btnLogin.setOnClickListener {
                login()
            }
        }
        loginViewModel.loginResponse.observe(viewLifecycleOwner){
                loginResponse->
            if (loginResponse != null) {
                onLoginResponse(loginResponse)
            }
        }
    }

    private fun login(){
        with(binding){
            val emailId = etEmailId.text.toString()
            val password = etPassword.text.toString()
            if(emailId.length<=0 || password.length<=0){
                showSnackbar(binding.root,"Enter all details")
                return
            }

            val loginRequest = LoginRequest(emailId,password)
            loginViewModel.loginUser(loginRequest)
            progressBar.visibility = View.VISIBLE

        }
    }

    private fun onLoginResponse(loginResponse: LoginResponse){
        binding.progressBar.visibility = View.GONE
        if(loginResponse.status==0){
            SharedPreferenceManager.saveBoolean(AppConstants.IS_LOGIN, true)
            SharedPreferenceManager.saveUser(loginResponse.user)
            navigateToHomeScreen()
            loginViewModel.clearLoginResponse()
        }else{
            showSnackbar(binding.root,"Invalid Credentials")
        }
    }

    private fun navigateToHomeScreen(){
        val nav = LoginFragmentDirections.actionLoginFragmentToHomeScreen()
        findNavController().navigate(nav)
    }

}