package com.sam.ecartapp.view.onBoardingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sam.ecartapp.AppConstants
import com.sam.ecartapp.SharedPreferenceManager
import com.sam.ecartapp.databinding.FragmentThirdOnboardingScreenBinding

class ThirdOnBoardingFragment :Fragment(){
    lateinit var binding: FragmentThirdOnboardingScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdOnboardingScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            SharedPreferenceManager.saveBoolean(AppConstants.IS_ONBOARDING_DONE,true)
            val action = ThirdOnBoardingFragmentDirections.actionThirdOnboardingToLoginFragment()
            findNavController().navigate(action)
        }
    }
}