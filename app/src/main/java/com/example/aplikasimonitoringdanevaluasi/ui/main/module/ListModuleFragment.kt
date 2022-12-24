package com.example.aplikasimonitoringdanevaluasi.ui.main.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListModuleBinding


class ListModuleFragment : Fragment() {

    private lateinit var binding: FragmentListModuleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListModuleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}