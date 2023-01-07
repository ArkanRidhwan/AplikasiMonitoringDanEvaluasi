package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListVideoBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.home.HomeAdminFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone


class ListVideoFragment : Fragment() {

    private lateinit var binding: FragmentListVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val role = getInstance(requireContext()).getString(Constant.ROLE)
        binding.apply {
            if (role == getString(R.string.student)) {
                fabAddVideo.gone()
            }
            fabAddVideo.setOnClickListener {
                val action =
                    HomeAdminFragmentDirections.actionHomeAdminFragmentToUploadVideoFragment()
                findNavController().navigate(action)
            }
        }
    }
}