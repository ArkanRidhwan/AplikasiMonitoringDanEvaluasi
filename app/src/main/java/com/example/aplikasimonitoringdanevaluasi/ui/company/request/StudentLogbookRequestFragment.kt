package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentStudentLogbookRequestBinding
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class StudentLogbookRequestFragment : Fragment() {

    private lateinit var binding: FragmentStudentLogbookRequestBinding
    private lateinit var logbookRequestAdapter: StudentLogbookRequestAdapter
    private val logbookRequestViewModel: StudentLogbookRequestViewModel by viewModels()
    private var studentId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentLogbookRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logbookRequestAdapter = StudentLogbookRequestAdapter()
        binding.apply {
            logbookRequestAdapter.onItemClick = {
                val action =
                    RequestFragmentDirections.actionRequestFragmentToStudentLogbookRequestDetailFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadLogbookStudent()
    }

    private fun loadLogbookStudent() {
        binding.apply {
            val userId = getInstance(requireContext()).getString(Constant.ID)
            recycleView.adapter = logbookRequestAdapter
            logbookRequestViewModel.getRequestLogbook(userId, "1").observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    logbookRequestAdapter.setListData(it)
                    recycleView.visible()
                } else {
                    recycleView.gone()
                }
            }
        }
    }
}