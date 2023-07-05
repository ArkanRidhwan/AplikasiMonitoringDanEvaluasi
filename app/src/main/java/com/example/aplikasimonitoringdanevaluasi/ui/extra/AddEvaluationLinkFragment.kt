package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddCourseBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddEvaluationLinkBinding
import com.example.aplikasimonitoringdanevaluasi.model.Evaluation
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.UploadModuleFragmentArgs
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.UploadVideoViewModel
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import java.util.UUID


class AddEvaluationLinkFragment : Fragment() {

    private lateinit var binding: FragmentAddEvaluationLinkBinding
    private val addEvaluationLinkViewModel: AddEvaluationLinkViewModel by viewModels()
    private val args: AddEvaluationLinkFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEvaluationLinkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAdd.setOnClickListener {
                val link = etLinkEvaluation.text.toString()
                val evaluation = Evaluation(
                    id = UUID.randomUUID().toString(),
                    link = link,
                    timestamp = System.currentTimeMillis().toString(),
                    courseId = args.tittleCourseId.toString()

                )
                addEvaluationLinkViewModel.saveEvaluation(evaluation).observe(viewLifecycleOwner) {
                    if (it == true) {
                        requireContext().showToast("Berhasil mengunggah link evaluasi")
                        requireActivity().onBackPressed()
                    } else {
                        requireContext().showToast("Gagal mengunggah link evaluasi")
                    }
                }
            }
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}