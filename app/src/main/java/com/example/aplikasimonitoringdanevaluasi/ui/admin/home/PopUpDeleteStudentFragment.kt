package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentPopUpDeleteStudentBinding


class PopUpDeleteStudentFragment(private val updateData: UpdateData) : DialogFragment() {

    private var _binding: FragmentPopUpDeleteStudentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopUpDeleteStudentBinding.inflate(layoutInflater, container, false)
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

