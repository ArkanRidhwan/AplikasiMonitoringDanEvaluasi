package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListCompanyBinding
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
                }
            }
        }
    }
}