package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentPopUpDeleteCourseBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentPopUpDeleteStudentBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.home.PopUpDeleteStudentFragment


class PopUpDeleteCourseFragment(private val updateData: PopUpDeleteCourseFragment.UpdateData) : DialogFragment() {

    private var _binding: FragmentPopUpDeleteCourseBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopUpDeleteCourseBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding?.btnCancel?.setOnClickListener {
            dismiss()
        }

        binding?.btnDelete?.setOnClickListener {
            dismiss()
            updateData.setDataUpdate(true)
            isCancelable = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface UpdateData {
        fun setDataUpdate(status: Boolean)
    }
}
