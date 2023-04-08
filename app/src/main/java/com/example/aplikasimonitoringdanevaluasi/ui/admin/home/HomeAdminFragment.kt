package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentHomeAdminBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class  HomeAdminFragment : Fragment() {

    private lateinit var binding: FragmentHomeAdminBinding
    private val homeAdminViewModel: HomeAdminViewModel by viewModels()
    private lateinit var homeAdminAdapter: HomeAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeAdminAdapter = HomeAdminAdapter()
        homeAdminAdapter.onItemClick = {
            val action =
                HomeAdminFragmentDirections.actionHomeAdminFragmentToDetailStudentAdminFragment(it)
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
        homeAdminViewModel.getFilter().observe(viewLifecycleOwner){
            val filteredItem = ArrayList<Student>()
            // loop through the array list to obtain the required value
            if (it != null) {
                for (item in it) {
                    if (item.name.toLowerCase().contains(e.toLowerCase())) {
                        filteredItem.add(item)
                    }
                }
            }
            // add the filtered value to adapter
            homeAdminAdapter.setListData(filteredItem)
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudent()
    }

    private fun loadStudent() {
        binding.apply {
            recycleView.adapter = homeAdminAdapter
            progressBar.visible()
            recycleView.gone()
            homeAdminViewModel.getAllStudent().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    homeAdminAdapter.setListData(it)
                    progressBar.gone()
                    recycleView.visible()
                } else {
                    progressBar.gone()
                    tvStudentNotCreated.visible()
                    recycleView.gone()
                }
            }
        }
    }
}