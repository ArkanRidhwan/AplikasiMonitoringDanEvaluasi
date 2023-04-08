package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentChooseCourseTittleBinding
import com.example.aplikasimonitoringdanevaluasi.model.Course


class ChooseCourseTittleFragment : Fragment() {

    private lateinit var binding: FragmentChooseCourseTittleBinding
    private val chooseCourseTittleViewModel: ChooseCourseTittleViewModel by viewModels()
    private lateinit var listCourse: ArrayList<Course>
    private lateinit var listNameCourse: ArrayList<String>
    private var course = Course()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseCourseTittleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            listCourse = ArrayList()
            listNameCourse = ArrayList()

            listCourse.clear()
            listNameCourse.clear()

            chooseCourseTittleViewModel.getCourse().observe(viewLifecycleOwner){
                if (it?.isNotEmpty() == true) {
                    listCourse.addAll(it)

                    for (i in listCourse) {
                        listNameCourse.add(i.tittle)
                    }
                    val dropdownAdapter =
                        ArrayAdapter(requireContext(), R.layout.dropdown_item, listNameCourse)
                    binding.autoCompleteTextView.setAdapter(dropdownAdapter)

                    binding.autoCompleteTextView.onItemClickListener =
                        AdapterView.OnItemClickListener { _, _, position, _ ->
                            course = listCourse[position]
                        }
                }
            }

            btnContinue.setOnClickListener {
                val action =
                    ChooseCourseTittleFragmentDirections.actionChooseCourseTittleFragmentToAddCourseFragment(
                        course
                    )
                findNavController().navigate(action)
            }
            btnAddCourseTittle.setOnClickListener {
                val action =
                    ChooseCourseTittleFragmentDirections.actionChooseCourseTittleFragmentToAddCourseTittleFragment()
                findNavController().navigate(action)
            }
        }
    }
}