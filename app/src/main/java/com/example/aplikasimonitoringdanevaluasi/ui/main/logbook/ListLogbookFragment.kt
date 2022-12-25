package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListLogbookBinding
import com.example.aplikasimonitoringdanevaluasi.ui.main.chat.ListContactChatFragmentArgs


class ListLogbookFragment : Fragment() {

    private lateinit var binding: FragmentListLogbookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {

        }
    }
}