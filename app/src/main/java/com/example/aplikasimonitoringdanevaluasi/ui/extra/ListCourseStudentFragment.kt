package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListCourseAdminBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListCourseStudentBinding
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListCourseStudentFragment : Fragment() {

    private lateinit var binding: FragmentListCourseStudentBinding
    private lateinit var listCourseAdapter: ListCourseAdapter
    private val listCourseViewModel: ListCourseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCourseStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listCourseAdapter = ListCourseAdapter()
        listCourseAdapter.onItemClick = {
            val action =
                ListCourseStudentFragmentDirections.actionListCourseStudentFragmentToDetailCourseFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        loadCourse()
    }

    private fun loadCourse() {
        binding.apply {
            recyclerView.adapter = listCourseAdapter
            progressBar.visible()
            recyclerView.gone()
            listCourseViewModel.getAllCourse().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listCourseAdapter.setListData(it)
                    progressBar.gone()
                    recyclerView.visible()
                } else {
                    progressBar.gone()
                }
            }
        }
    }
}