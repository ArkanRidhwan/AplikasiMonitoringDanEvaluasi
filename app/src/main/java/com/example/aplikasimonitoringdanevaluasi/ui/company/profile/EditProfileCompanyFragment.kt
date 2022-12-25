package com.example.aplikasimonitoringdanevaluasi.ui.company.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentEditProfileCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListCompanyBinding


class EditProfileCompanyFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileCompanyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}