package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListCompanyFragment : Fragment() {

    private lateinit var binding: FragmentListCompanyBinding
    private val listCompanyViewModel: ListCompanyViewModel by viewModels()
    private lateinit var listCompanyAdapter: ListCompanyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listCompanyAdapter = ListCompanyAdapter()
        listCompanyAdapter.onItemClick = {
            val action =
                HomeStudentFragmentDirections.actionHomeStudentFragmentToDetailCompanyStudentFragment(
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
        listCompanyViewModel.getFilter().observe(viewLifecycleOwner){
            val filteredItem = ArrayList<Company>()
            // loop through the array list to obtain the required value
            if (it != null) {
                for (item in it) {
                    if (item.companyName.toLowerCase().contains(e.toLowerCase())) {
                        filteredItem.add(item)
                    }
                }
            }
            // add the filtered value to adapter
            listCompanyAdapter.setListData(filteredItem)
        }
    }

    override fun onResume() {
        super.onResume()
        loadCompany()
    }

    private fun loadCompany() {
        binding.apply {
            recycleView.adapter = listCompanyAdapter
            progressBar.visible()
            recycleView.gone()
            listCompanyViewModel.getCompany().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listCompanyAdapter.setListData(it)
                    progressBar.gone()
                    recycleView.visible()
                } else {
                    progressBar.gone()
                    tvCompanyListNotCreated.visible()
                    recycleView.gone()
                }
            }
        }
    }
}