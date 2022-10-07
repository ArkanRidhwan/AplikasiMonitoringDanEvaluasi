package com.example.aplikasimonitoringdanevaluasi.ui.company.studentRequest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentProfileCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentStudentRequestCompanyBinding

class StudentRequestCompanyFragment : Fragment() {

    private lateinit var binding: FragmentStudentRequestCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentRequestCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button3.setOnClickListener {
            findNavController().navigate(StudentRequestCompanyFragmentDirections.actionStudentRequestCompanyFragmentToWelcomeFragment())
        }
    }
}