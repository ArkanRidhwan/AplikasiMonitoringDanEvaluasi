package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentStudentRequestBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.google.firebase.ktx.Firebase

class StudentRequestFragment : Fragment() {

    private lateinit var binding: FragmentStudentRequestBinding
    private lateinit var studentRequestAdapter: StudentRequestAdapter
    private val studentRequestViewModel: StudentRequestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentRequestAdapter = StudentRequestAdapter()
        studentRequestAdapter.onItemClick = {
            val action =
                RequestFragmentDirections.actionRequestFragmentToDetailRequestStudentFragment(
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
        studentRequestViewModel.getFilter().observe(viewLifecycleOwner){
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
            studentRequestAdapter.setListData(filteredItem)
        }
    }


    override fun onResume() {
        super.onResume()
        loadRequestStudent()
    }

    private fun loadRequestStudent() {
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            recycleView.adapter = studentRequestAdapter
            studentRequestViewModel.getRequestStudent("1", userId).observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    recycleView.gone()
                    studentRequestAdapter.setListData(it)
                    progressBar.gone()
                    recycleView.visible()
                } else {
                    tvStudentRequestNotCreated.visible()
                    progressBar.gone()
                    recycleView.gone()
                }
            }
        }
    }
}