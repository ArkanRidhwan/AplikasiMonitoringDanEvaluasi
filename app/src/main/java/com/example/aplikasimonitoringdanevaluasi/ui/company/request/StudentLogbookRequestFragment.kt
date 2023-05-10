package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentStudentLogbookRequestBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class StudentLogbookRequestFragment : Fragment() {

    private lateinit var binding: FragmentStudentLogbookRequestBinding
    private lateinit var logbookRequestAdapter: StudentLogbookRequestAdapter
    private val logbookRequestViewModel: StudentLogbookRequestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentLogbookRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logbookRequestAdapter = StudentLogbookRequestAdapter()
        binding.apply {
            logbookRequestAdapter.onItemClick = {
                val action =
                    RequestFragmentDirections.actionRequestFragmentToStudentLogbookRequestDetailFragment(
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
        logbookRequestViewModel.getFilter().observe(viewLifecycleOwner){
            val filteredItem = ArrayList<Logbook>()
            // loop through the array list to obtain the required value
            if (it != null) {
                for (item in it) {
                    if (item.name.toLowerCase().contains(e.toLowerCase())) {
                        filteredItem.add(item)
                    }
                }
            }
            // add the filtered value to adapter
            logbookRequestAdapter.setListData(filteredItem)
        }
    }

    override fun onResume() {
        super.onResume()
        loadLogbookStudent()
    }

    private fun loadLogbookStudent() {
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            recycleView.adapter = logbookRequestAdapter
            logbookRequestViewModel.getRequestLogbook(userId, "1").observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    recycleView.gone()
                    logbookRequestAdapter.setListData(it)
                    progressBar.gone()
                    recycleView.visible()
                    tvLogbookNotCreated.gone()
                } else {
                    tvLogbookNotCreated.visible()
                    progressBar.gone()
                    recycleView.gone()
                }
            }
        }
    }
}