package com.example.aplikasimonitoringdanevaluasi.ui.main.module

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDownloadModuleBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListModuleBinding


class DownloadModuleFragment : Fragment() {

    private lateinit var binding: FragmentDownloadModuleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadModuleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}