package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListContactChatAdminBinding
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
                }
            }
        }
    }
}