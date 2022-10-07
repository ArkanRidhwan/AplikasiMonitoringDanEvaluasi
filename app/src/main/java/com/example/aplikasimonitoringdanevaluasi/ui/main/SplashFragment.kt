package com.example.aplikasimonitoringdanevaluasi.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentSplashBinding
import com.example.aplikasimonitoringdanevaluasi.utils.Constant.ID
import com.example.aplikasimonitoringdanevaluasi.utils.Constant.ROLE
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val role = getInstance(requireContext()).getString(ROLE)
        val userId = getInstance(requireContext()).getString(ID)

        Handler().postDelayed({
            if (userId.isNotEmpty()) {
                when (role) {
                    getString(R.string.student) -> {
                        findNavController().navigate(SplashFragmentDirections.actionSplashScreenFragmentToHomeStudentFragment())
                    }
                    getString(R.string.company) -> {
                        findNavController().navigate(SplashFragmentDirections.actionSplashScreenFragmentToHomeCompanyFragment())
                    }
                    else -> {
                        findNavController().navigate(SplashFragmentDirections.actionSplashScreenFragmentToHomeAdminFragment())
                    }
                }
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashScreenFragmentToWelcomeFragment())
            }
        }, 1000)
    }
}