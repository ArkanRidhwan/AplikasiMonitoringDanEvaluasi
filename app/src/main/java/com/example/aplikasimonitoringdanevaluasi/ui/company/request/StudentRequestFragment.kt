package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentStudentRequestBinding
import com.example.aplikasimonitoringdanevaluasi.utils.*

class StudentRequestFragment : Fragment() {

    private lateinit var binding: FragmentStudentRequestBinding
    private lateinit var studentRequestAdapter: StudentRequestAdapter
    private val studentRequestViewModel: StudentRequestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studentRequestAdapter = StudentRequestAdapter()
        studentRequestAdapter.onItemClick = {
            val action =
                RequestFragmentDirections.actionRequestFragmentToDetailRequestStudentFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        loadRequestStudent()
    }

    private fun loadRequestStudent() {
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            recycleView.adapter = studentRequestAdapter
            studentRequestViewModel.getRequestStudent("2", userId).observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    studentRequestAdapter.setListData(it)
                    recycleView.visible()
                } else {
                    recycleView.gone()
                }
            }
        }
    }
}