package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentHomeAdminBinding
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class HomeAdminFragment : Fragment() {

    private lateinit var binding: FragmentHomeAdminBinding
    private val homeAdminViewModel: HomeAdminViewModel by viewModels()
    private lateinit var homeAdminAdapter: HomeAdminAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeAdminAdapter = HomeAdminAdapter()
        homeAdminAdapter.onItemClick = {
            val action =
                HomeAdminFragmentDirections.actionHomeAdminFragmentToDetailStudentAdminFragment(it)
            findNavController().navigate(action)
        }

        binding.apply {
            fabChatAdmin.setOnClickListener {
                findNavController().navigate(
                    HomeAdminFragmentDirections.actionHomeAdminFragmentToListContactChatFragment(
                        /* getString(R.string.admin)*/
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudent()
    }

    private fun loadStudent() {
        binding.apply {
            recycleView.adapter = homeAdminAdapter
            progressBar.visible()
            recycleView.gone()
            homeAdminViewModel.getAllStudent().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    homeAdminAdapter.setListData(it)
                    progressBar.gone()
                    recycleView.visible()
                } else {
                    progressBar.gone()
                }
            }
        }
    }
}