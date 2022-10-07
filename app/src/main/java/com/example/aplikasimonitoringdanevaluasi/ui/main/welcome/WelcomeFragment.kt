package com.example.aplikasimonitoringdanevaluasi.ui.main.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAdmin.setOnClickListener {
                val action =
                    WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment(getString(R.string.admin))
                findNavController().navigate(action)
            }
            btnCompany.setOnClickListener {
                val action =
                    WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment(getString(R.string.company))
                findNavController().navigate(action)
            }
            btnStudent.setOnClickListener {
                val action =
                    WelcomeFragmentDirections.actionWelcomeFragmentToLoginFragment(getString(R.string.student))
                findNavController().navigate(action)
            }
        }
    }
}