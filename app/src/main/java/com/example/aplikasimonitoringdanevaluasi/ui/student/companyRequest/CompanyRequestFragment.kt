package com.example.aplikasimonitoringdanevaluasi.ui.student.companyRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentCompanyRequestBinding


class CompanyRequestFragment : Fragment() {

    private lateinit var binding: FragmentCompanyRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompanyRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}