package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListCourseAdminBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListCourseStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Course
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
        binding.apply {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { searchQuery(it) }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let { searchQuery(it) }
                    return true
                }

            })
        }
    }

    private fun searchQuery(e: String) {
        listCourseViewModel.getFilter().observe(viewLifecycleOwner){
            val filteredItem = ArrayList<Course>()
            // loop through the array list to obtain the required value
            if (it != null) {
                for (item in it) {
                    if (item.tittle.toLowerCase().contains(e.toLowerCase())) {
                        filteredItem.add(item)
                    }
                }
            }
            // add the filtered value to adapter
            listCourseAdapter.setListData(filteredItem)
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
                    tvCourseNotCreated.visible()
                    recyclerView.gone()
                }
            }
        }
    }
}