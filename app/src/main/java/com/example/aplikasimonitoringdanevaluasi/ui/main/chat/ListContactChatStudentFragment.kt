package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListContactChatStudentBinding
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
        /*val role = getInstance(requireContext()).getString(Constant.ROLE)
        if (role == getString(R.string.student))
            binding.apply {
                ivBack.gone()
                tvTittle.gone()
                tittle.gone()
            }*/
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
        /*listAdminAdapter.onItemClick = {
            val action =
                ListContactChatStudentFragmentDirections.actionListContactChatFragmentToChatFragment(
                    it.id,
                    it.name,
                    it.image
                )
            findNavController().navigate(action)
        }*/
    }

    override fun onResume() {
        super.onResume()
        loadAdmin()
    }

    /*private fun loadStudent() {
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
    }*/

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