package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentHomeAdminBinding


class HomeAdminFragment : Fragment() {

    private lateinit var binding: FragmentHomeAdminBinding
    private val args: HomeAdminFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fabChatAdmin.setOnClickListener {
                findNavController().navigate(
                    HomeAdminFragmentDirections.actionHomeAdminFragmentToListContactChatFragment(
                        getString(
                            R.string.admin
                        )
                    )
                )
            }
        }
    }
}