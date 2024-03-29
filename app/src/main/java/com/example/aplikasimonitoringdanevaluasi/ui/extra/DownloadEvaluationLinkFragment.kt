package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddEvaluationLinkBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDownloadEvaluationLinkBinding


class DownloadEvaluationLinkFragment : Fragment() {

    private lateinit var binding: FragmentDownloadEvaluationLinkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadEvaluationLinkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

        }
    }
}