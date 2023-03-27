package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListContactChatStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListContactChatStudentFragment : Fragment() {

    private val listAdminViewModel: ListAdminViewModel by viewModels()
    private lateinit var binding: FragmentListContactChatStudentBinding
    private lateinit var listAdminAdapter: ListAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListContactChatStudentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdminAdapter = ListAdminAdapter()
        listAdminAdapter.onItemClick = {
            val action =
                ListContactChatStudentFragmentDirections.actionListContactChatFragmentToChatFragment(
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
        listAdminViewModel.getFilter().observe(viewLifecycleOwner) {
            val filteredItem = ArrayList<Admin>()
            // loop through the array list to obtain the required value
            if (it != null) {
                for (item in it) {
                    if (item.name.toLowerCase().contains(e.toLowerCase())) {
                        filteredItem.add(item)
                    }
                }
            }
            // add the filtered value to adapter
            listAdminAdapter.setListData(filteredItem)
        }
    }

    override fun onResume() {
        super.onResume()
        loadAdmin()
    }


    private fun loadAdmin() {
        binding.apply {
            rvChatListStudent.adapter = listAdminAdapter
            progressBar.visible()
            rvChatListStudent.gone()
            listAdminViewModel.getAdmin().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listAdminAdapter.setListData(it)
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