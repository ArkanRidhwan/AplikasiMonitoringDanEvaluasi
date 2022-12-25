package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddLogbookBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListVideoBinding


class AddLogbookFragment : Fragment() {

    private lateinit var binding: FragmentAddLogbookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}