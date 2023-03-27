package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListContactChatAdminBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListContactChatAdminFragment : Fragment() {

    private lateinit var binding: FragmentListContactChatAdminBinding
    private lateinit var listStudentAdapter: ListStudentAdapter
    private val listStudentViewModel: ListStudentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListContactChatAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listStudentAdapter = ListStudentAdapter()
        listStudentAdapter.onItemClick = {
            val action =
                ListContactChatAdminFragmentDirections.actionListContactChatAdminFragmentToChatFragment(
                    it.id,
                    it.name,
                    it.image
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
        val userId = getInstance(requireContext()).getString(Constant.ID)
        listStudentViewModel.getFilter().observe(viewLifecycleOwner){
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
            listStudentAdapter.setListData(filteredItem)
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudent()
    }

    private fun loadStudent() {
        binding.apply {
            rvChatListStudent.adapter = listStudentAdapter
            progressBar.visible()
            rvChatListStudent.gone()
            listStudentViewModel.getStudent().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listStudentAdapter.setListData(it)
                    progressBar.gone()
                    rvChatListStudent.visible()
                } else {
                    progressBar.gone()
                    tvContactNotCreated.visible()
                    rvChatListStudent.gone()
                }
            }
        }
    }
}