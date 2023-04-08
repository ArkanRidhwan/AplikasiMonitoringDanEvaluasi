package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailReportBinding
import com.example.aplikasimonitoringdanevaluasi.utils.gone


class  DetailReportFragment : Fragment() {

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
                progressBar.gone()
                tvName.text = it?.studentName.toString()
                tvDate.text = it?.date.toString()
                tvBartittle.text = it?.tittle.toString()
                tvAnswer1.text = it?.answer1.toString()
                tvAnswer2.text = it?.answer2.toString()
                tvAnswer3.text = it?.answer3.toString()
                tvAnswer4.text = it?.answer4.toString()
                tvAnswer5.text = it?.answer5.toString()
                tvAnswer6.text = it?.answer6.toString()
                tvAnswer7.text = it?.answer7.toString()
                tvAnswer8.text = it?.answer8.toString()
                tvAnswer9.text = it?.answer9.toString()
                tvAnswer10.text = it?.answer10.toString()
                tvAnswer11.text = it?.answer11.toString()
                tvAnswer12.text = it?.answer12.toString()
                tvAnswer13.text = it?.answer13.toString()
                tvAnswer14.text = it?.answer14.toString()
                tvAnswer15.text = it?.answer15.toString()
                tvAnswer16.text = it?.answer16.toString()
                tvAnswer17.text = it?.answer17.toString()
                tvAnswer18.text = it?.answer18.toString()
            }
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}