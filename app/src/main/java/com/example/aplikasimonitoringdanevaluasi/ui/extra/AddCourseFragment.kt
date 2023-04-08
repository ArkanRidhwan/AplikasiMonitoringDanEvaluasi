package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddCourseBinding


class AddCourseFragment : Fragment() {

    private lateinit var binding: FragmentAddCourseBinding
    private val args: AddCourseFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCourseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvBartittle.text = args.course.tittle
            cvVideo.setOnClickListener {
                val action =
                    AddCourseFragmentDirections.actionAddCourseFragmentToUploadVideoFragment(
                        args.course.id
                    )
                findNavController().navigate(action)
            }

            cvModule.setOnClickListener{
                val action =
                    AddCourseFragmentDirections.actionAddCourseFragmentToUploadModuleFragment(
                        args.course.id
                    )
                findNavController().navigate(action)
            }

            cvEvaluationLink.setOnClickListener {
                val action =
                    AddCourseFragmentDirections.actionAddCourseFragmentToAddEvaluationLinkFragment(
                        args.course.id
                    )
                findNavController().navigate(action)
            }
        }
    }
}