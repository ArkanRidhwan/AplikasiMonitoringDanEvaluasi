package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailLogbookBinding


class DetailLogbookFragment : Fragment() {

    private lateinit var binding: FragmentDetailLogbookBinding
    private val args: DetailLogbookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            tvDate.text = "Dibuat pada: ${args.logbook.date}"
            tvContentLogbook.text = args.logbook.content
            tvActivityDate.text = "Tanggal kegiatan: ${args.logbook.activityDate}"
        }
    }
}