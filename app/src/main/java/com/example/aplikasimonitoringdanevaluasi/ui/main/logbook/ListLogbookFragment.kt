package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListLogbookBinding
import com.example.aplikasimonitoringdanevaluasi.ui.student.home.HomeStudentFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListLogbookFragment : Fragment() {

    private lateinit var binding: FragmentListLogbookBinding
    private lateinit var listLogbookAdapter: ListLogbookAdapter
    private val listLogbookViewModel: ListLogbookViewModel by viewModels()
    private val args: ListLogbookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val role = getInstance(requireContext()).getString(Constant.ROLE)
        listLogbookAdapter = ListLogbookAdapter()
        binding.apply {
            when (role) {
                getString(R.string.student) -> {
                    ivBack.gone()
                    tvBartittle.gone()
                    ivBar.gone()
                }
                getString(R.string.admin) -> {
                    fabAddLogbook.gone()
                }
                getString(R.string.company) -> {
                    fabAddLogbook.gone()
                }
            }

            fabAddLogbook.setOnClickListener {
                val action =
                    HomeStudentFragmentDirections.actionHomeStudentFragmentToAddLogbookFragment()
                findNavController().navigate(action)
            }

            listLogbookAdapter.onItemClick = {
                when (role) {
                    getString(R.string.student) -> {
                        val action =
                            HomeStudentFragmentDirections.actionHomeStudentFragmentToDetailLogbookFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                    getString(R.string.admin) -> {
                        val action =
                            ListLogbookFragmentDirections.actionListLogbookFragmentToDetailLogbookFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                    getString(R.string.company) -> {
                        val action =
                            ListLogbookFragmentDirections.actionListLogbookFragmentToDetailLogbookFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when (getInstance(requireContext()).getString(Constant.ROLE)) {
            getString(R.string.student) -> loadLogbookStudent()
            getString(R.string.admin) -> loadLogbookAdmin()
            getString(R.string.company) -> loadLogbookCompany()
        }
    }

    private fun loadLogbookCompany() {
        binding.apply {
            val studentId = args.studentId
            recyclerView.adapter = listLogbookAdapter
            progressBar.visible()
            recyclerView.gone()
            if (studentId != null) {
                listLogbookViewModel.getAdminLogbook(studentId).observe(viewLifecycleOwner) {
                    if (it?.isNotEmpty() == true) {
                        listLogbookAdapter.setListData(it)
                        progressBar.gone()
                        recyclerView.visible()
                    } else {
                        progressBar.gone()
                    }
                }
            }
        }
    }

    private fun loadLogbookAdmin() {
        binding.apply {
            val studentId = args.student?.id
            recyclerView.adapter = listLogbookAdapter
            progressBar.visible()
            recyclerView.gone()
            if (studentId != null) {
                listLogbookViewModel.getAdminLogbook(studentId).observe(viewLifecycleOwner) {
                    if (it?.isNotEmpty() == true) {
                        listLogbookAdapter.setListData(it)
                        progressBar.gone()
                        recyclerView.visible()
                    } else {
                        progressBar.gone()
                    }
                }
            }
        }
    }

    private fun loadLogbookStudent() {
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            recyclerView.adapter = listLogbookAdapter
            progressBar.visible()
            recyclerView.gone()
            listLogbookViewModel.getStudentLogbook(userId).observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listLogbookAdapter.setListData(it)
                    progressBar.gone()
                    recyclerView.visible()
                } else {
                    progressBar.gone()
                }
            }
        }
    }
}