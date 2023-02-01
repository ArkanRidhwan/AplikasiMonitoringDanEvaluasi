package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentHomeCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.utils.*


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
            homeCompanyViewModel.getRequestStudent("3", userId).observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    homeCompanyAdapter.setListData(it)
                    recycleView.visible()
                } else {
                    recycleView.gone()
                }
            }
        }
    }
}