package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddCourseTittleBinding
import com.example.aplikasimonitoringdanevaluasi.model.Course
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import java.util.*


class AddCourseTittleFragment : Fragment() {

    private lateinit var binding: FragmentAddCourseTittleBinding
    private val courseTittleViewModel: CourseTittleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCourseTittleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnAdd.setOnClickListener {
                val courseTittle = etCourseTittle.text.toString()
                val courseNumber = etCourseNumber.text.toString()

                val course = Course(
                    id = UUID.randomUUID().toString(),
                    tittle = courseTittle,
                    number = courseNumber,
                    timestamp = System.currentTimeMillis().toString()
                )

                courseTittleViewModel.saveCourse(course)
                    .observe(viewLifecycleOwner) { data ->
                        if (data == true) {
                            val action =
                                AddCourseTittleFragmentDirections.actionCourseTittleFragmentToAddCourseFragment(courseTittle
                                )
                            findNavController().navigate(action)
                            requireContext().showToast("Data berhasil disimpan")
                        } else {
                            requireContext().showToast("Data gagal disimpan")
                        }
                    }
            }
        }
    }
}