package com.sam.ecartapp.view.onBoardingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sam.ecartapp.AppConstants
import com.sam.ecartapp.SharedPreferenceManager
import com.sam.ecartapp.databinding.FragmentSecondOnboardingScreenBinding

class SecondOnBoardingFragment : Fragment() {
    lateinit var binding:FragmentSecondOnboardingScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondOnboardingScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            val action = SecondOnBoardingFragmentDirections.actionSecondOnboardingToThirdOnboarding()
            findNavController().navigate(action)
        }
        binding.btnSkip.setOnClickListener {
            SharedPreferenceManager.saveBoolean(AppConstants.IS_ONBOARDING_DONE,true)
            navigateToLoginScreen()
        }
    }

    private fun navigateToLoginScreen() {
        val action = SecondOnBoardingFragmentDirections.actionSecondOnboardingToLoginFragment()
        findNavController().navigate(action)
    }
}