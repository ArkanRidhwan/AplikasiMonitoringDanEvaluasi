package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListReportAdminBinding
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListReportAdminFragment : Fragment() {

    private lateinit var binding: FragmentListReportAdminBinding
    private lateinit var listReportAdminAdapter: ListReportAdminAdapter
    private val listReportAdminViewModel: ListReportAdminViewModel by viewModels()
    private val args: ListReportAdminFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListReportAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listReportAdminAdapter = ListReportAdminAdapter()
        binding.apply {
            listReportAdminAdapter.onItemClick = {
                val action =
                    ListReportAdminFragmentDirections.actionListReportAdminFragmentToDetailReportFragment(
                        args.studentId
                    )
                findNavController().navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadReport()
    }

    private fun loadReport() {
        binding.apply {
            recyclerView.adapter = listReportAdminAdapter
            progressBar.visible()
            recyclerView.gone()
            listReportAdminViewModel.getReport(args.studentId).observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listReportAdminAdapter.setListData(it)
                    progressBar.gone()
                    recyclerView.visible()
                } else {
                    tvReportNotCreated.visible()
                    progressBar.gone()
                }
            }
        }
    }
}