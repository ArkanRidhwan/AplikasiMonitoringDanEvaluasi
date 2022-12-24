package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentHomeAdminBinding


class DetailStudentCompanyFragment : Fragment() {

    private lateinit var binding: FragmentDetailStudentCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailStudentCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}