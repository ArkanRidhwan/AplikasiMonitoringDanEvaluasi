package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentHomeCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class HomeCompanyFragment : Fragment() {

    private lateinit var binding: FragmentHomeCompanyBinding
    private lateinit var homeCompanyAdapter: HomeCompanyAdapter
    private val homeCompanyViewModel: HomeCompanyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeCompanyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            homeCompanyAdapter = HomeCompanyAdapter()
            homeCompanyAdapter.onItemClick = {
                val action =
                    HomeCompanyFragmentDirections.actionHomeCompanyFragmentToDetailStudentCompanyFragment(
                        it
                    )
                findNavController().navigate(action)
            }
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
        val userId = getInstance(requireContext()).getString(Constant.ID)
        homeCompanyViewModel.getFilter("2", userId).observe(viewLifecycleOwner){
            val filteredItem = ArrayList<RequestStudent>()
            // loop through the array list to obtain the required value
            if (it != null) {
                for (item in it) {
                    if (item.studentName.toLowerCase().contains(e.toLowerCase())) {
                        filteredItem.add(item)
                    }
                }
            }
            // add the filtered value to adapter
            homeCompanyAdapter.setListData(filteredItem)
        }
    }

    override fun onResume() {
        super.onResume()
        loadApprovedStudent()
    }

    private fun loadApprovedStudent() {
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            recycleView.adapter = homeCompanyAdapter
            homeCompanyViewModel.getApprovedStudent("2", userId).observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    recycleView.gone()
                    homeCompanyAdapter.setListData(it)
                    progressBar.gone()
                    recycleView.visible()
                } else {
                    tvReportNotCreated.visible()
                    progressBar.gone()
                    recycleView.gone()
                }
            }
        }
    }
}