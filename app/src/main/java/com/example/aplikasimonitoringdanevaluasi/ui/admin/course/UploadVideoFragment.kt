package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentUploadVideoBinding


class UploadVideoFragment : Fragment() {

    private lateinit var binding: FragmentUploadVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
                /*val action =
                    UploadVideoFragmentDirections.actionUploadVideoFragmentToCourseAdminFragment()
                findNavController().navigate(action)*/
            }
        }
    }
}