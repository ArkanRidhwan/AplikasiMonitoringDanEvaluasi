package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentAdminBinding


class DetailStudentAdminFragment : Fragment() {

    private lateinit var binding: FragmentDetailStudentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailStudentAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}