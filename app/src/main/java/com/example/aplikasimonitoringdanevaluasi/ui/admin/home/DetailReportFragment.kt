package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailReportBinding


class DetailReportFragment : Fragment() {

    private lateinit var binding: FragmentDetailReportBinding
    private val detailReportViewModel: DetailReportViewModel by viewModels()
    private val args: DetailReportFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailReportBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            detailReportViewModel.getReportById(args.studentId).observe(viewLifecycleOwner) {
                tvName.text = it?.studentName.toString()
                tvDate.text = it?.date.toString()
                tvAnswer1.text = it?.answer1.toString()
                tvAnswer2.text = it?.answer2.toString()
                tvAnswer3.text = it?.answer3.toString()
                tvAnswer4.text = it?.answer4.toString()
                tvAnswer5.text = it?.answer5.toString()
                tvAnswer6.text = it?.answer6.toString()
                tvAnswer7.text = it?.answer7.toString()
                tvAnswer8.text = it?.answer8.toString()
            }
        }
    }
}