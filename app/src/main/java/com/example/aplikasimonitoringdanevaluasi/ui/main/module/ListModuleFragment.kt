package com.example.aplikasimonitoringdanevaluasi.ui.main.module

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListModuleBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.CourseAdminFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.ui.student.course.CourseStudentFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone


class ListModuleFragment : Fragment() {

    private lateinit var binding: FragmentListModuleBinding
    private lateinit var listModuleAdapter: ListModuleAdapter
    private val listModuleViewModel: ListModuleViewModel by viewModels()

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

            listModuleAdapter = ListModuleAdapter()
            recyclerView.adapter = listModuleAdapter
            listModuleViewModel.getAllModule().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listModuleAdapter.setListData(it)
                } else {

                }
            }
            listModuleAdapter.onItemClick = {
                when (role) {
                    getString(R.string.admin) -> {
                        val action =
                            CourseAdminFragmentDirections.actionCourseAdminFragmentToDownloadModuleFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                    getString(R.string.student) -> {
                        val action =
                            CourseStudentFragmentDirections.actionCourseStudentFragmentToDownloadModuleFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}