package com.example.aplikasimonitoringdanevaluasi.ui.main.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListModuleBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.CourseAdminFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.ui.admin.home.HomeAdminFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone


class ListModuleFragment : Fragment() {

    private lateinit var binding: FragmentListModuleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListModuleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val role = getInstance(requireContext()).getString(Constant.ROLE)
        binding.apply {
            if (role == getString(R.string.student)) {
                fabAddModule.gone()
            }
            fabAddModule.setOnClickListener {
                /*val action =
                    HomeAdminFragmentDirections.actionHomeAdminFragmentToUploadModuleFragment()
                findNavController().navigate(action)*/
                val action =
                    CourseAdminFragmentDirections.actionCourseAdminFragmentToUploadModuleFragment()
                findNavController().navigate(action)
            }
        }
    }
}