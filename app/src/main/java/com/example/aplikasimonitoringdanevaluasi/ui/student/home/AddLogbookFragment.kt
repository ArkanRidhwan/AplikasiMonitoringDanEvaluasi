package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddLogbookBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.UploadVideoViewModel
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getDateNow
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import java.util.*


class AddLogbookFragment : Fragment() {

    private lateinit var binding: FragmentAddLogbookBinding
    private val uploadLogbookViewModel: AddLogbookViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnSave.setOnClickListener {
                val content = etContentLogbook.text.toString()
                val name = getInstance(requireContext()).getString(Constant.NAME)
                val logbookId = UUID.randomUUID().toString()
                val logbookUserId = getInstance(requireContext()).getString(Constant.ID)
                val logbook = Logbook(
                    id = logbookId,
                    logbookUserId = logbookUserId,
                    name = name,
                    content = content,
                    date = getDateNow()
                )
                uploadLogbookViewModel.saveLogbook(logbook)
                    .observe(viewLifecycleOwner) { data ->
                        if (data == true) {
                            requireActivity().onBackPressed()
                            requireContext().showToast("Menyimpan logbook berhasil")
                        } else {
                            requireContext().showToast("Menyimpan logbook gagal")
                        }
                    }
            }
        }
    }
}