package com.example.aplikasimonitoringdanevaluasi.ui.admin.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentEditProfileAdminBinding


class EditProfileAdminFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}