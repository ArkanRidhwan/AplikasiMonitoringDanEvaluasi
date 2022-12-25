package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailLogbookBinding


class DetailLogbookFragment : Fragment() {

    private lateinit var binding: FragmentDetailLogbookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}