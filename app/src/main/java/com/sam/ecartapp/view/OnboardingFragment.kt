package com.sam.ecartapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sam.ecartapp.AppConstants
import com.sam.ecartapp.SharedPreferenceManager
import com.sam.ecartapp.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(SharedPreferenceManager.getBoolean(AppConstants.IS_ONBOARDING_DONE)){
            navigateToLoginScreen()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            SharedPreferenceManager.saveBoolean(AppConstants.IS_ONBOARDING_DONE,true)
            navigateToLoginScreen()
        }
    }

    private fun navigateToLoginScreen(){
        val action = OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
        findNavController().navigate(action)
    }



}
