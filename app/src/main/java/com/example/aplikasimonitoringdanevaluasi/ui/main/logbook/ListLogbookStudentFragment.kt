package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListLogbookStudentBinding
import com.example.aplikasimonitoringdanevaluasi.ui.student.home.HomeStudentFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListLogbookStudentFragment : Fragment() {

    private lateinit var binding: FragmentListLogbookStudentBinding
    private lateinit var listLogbookAdapter: ListLogbookAdapter
    private val listLogbookViewModel: ListLogbookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListLogbookStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listLogbookAdapter = ListLogbookAdapter()
        binding.apply {
            fabAddLogbook.setOnClickListener {
                val action =
                    HomeStudentFragmentDirections.actionHomeStudentFragmentToAddLogbookFragment()
                findNavController().navigate(action)
            }
        }
        listLogbookAdapter.onItemClick = {
            val action =
                HomeStudentFragmentDirections.actionHomeStudentFragmentToDetailLogbookFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        loadLogbookAdmin()
    }

    private fun loadLogbookAdmin() {
        binding.apply {
            val studentId = getInstance(requireContext()).getString(Constant.ID)
            recyclerView.adapter = listLogbookAdapter
            progressBar.visible()
            recyclerView.gone()
            listLogbookViewModel.getLogbook(studentId).observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listLogbookAdapter.setListData(it)
                    progressBar.gone()
                    recyclerView.visible()
                    tvLogbookNotCreated.gone()
                } else {
                    tvLogbookNotCreated.visible()
                    progressBar.gone()
                    recyclerView.gone()
                }
            }
        }
    }
}
