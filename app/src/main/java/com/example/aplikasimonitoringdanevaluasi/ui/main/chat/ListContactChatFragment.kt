package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListContactChatBinding
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListContactChatFragment : Fragment() {

    private val listStudentViewModel: ListStudentViewModel by viewModels()
    private val listAdminViewModel: ListAdminViewModel by viewModels()
    private lateinit var binding: FragmentListContactChatBinding
    private lateinit var listStudentAdapter: ListStudentAdapter
    private lateinit var listAdminAdapter: ListAdminAdapter
    private val args: ListContactChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListContactChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listStudentAdapter = ListStudentAdapter()
        listAdminAdapter = ListAdminAdapter()
        listStudentAdapter.onItemClick = {
            val action =
                ListContactChatFragmentDirections.actionListContactChatFragmentToChatFragment(
                    it.id,
                    it.name
                )
            findNavController().navigate(action)
        }
        listAdminAdapter.onItemClick = {
            val action =
                ListContactChatFragmentDirections.actionListContactChatFragmentToChatFragment(
                    it.id,
                    it.name
                )
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        if (args.role == getString(R.string.admin)) {
            loadStudent()
        } else {
            loadAdmin()
        }
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
                }
            }
        }
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
                }
            }
        }
    }
}