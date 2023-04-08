package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentLogbookRequestBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.utils.showToast


class DetailStudentLogbookRequestFragment : Fragment() {

    private lateinit var binding: FragmentDetailStudentLogbookRequestBinding
    private val args: DetailStudentLogbookRequestFragmentArgs by navArgs()
    private val detailStudentLogbookRequestViewModel: DetailStudentLogbookRequestViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentDetailStudentLogbookRequestBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvActivityDate.text = args.logbook.activityDate
            tvContentLogbook.text = args.logbook.content
            tvDate.text = "Dibuat pada: ${args.logbook.date}"

            val logbook = Logbook(
                id = args.logbook.id,
                logbookUserId = args.logbook.logbookUserId,
                companyId = args.logbook.companyId,
                name = args.logbook.name,
                activityDate = args.logbook.activityDate,
                content = args.logbook.content,
                date = args.logbook.date,
                timestamp = args.logbook.timestamp,
                image = args.logbook.image
            )

            btnAccept.setOnClickListener {
                detailStudentLogbookRequestViewModel.updateLogbookStatus(
                    logbook,
                    args.logbook.id, "3"
                )
                    .observe(viewLifecycleOwner) {
                        if (it == true) {
                            requireContext().showToast("Logbook berhasil diproses")
                            requireActivity().onBackPressed()
                        } else {
                            requireContext().showToast("Logbook gagal diproses")
                        }
                    }
            }

            btnReject.setOnClickListener {
                detailStudentLogbookRequestViewModel.updateLogbookStatus(
                    logbook,
                    args.logbook.id, "2"
                )
                    .observe(viewLifecycleOwner) {
                        if (it == true) {
                            requireContext().showToast("Logbook berhasil diproses")
                            requireActivity().onBackPressed()
                        } else {
                            requireContext().showToast("Logbook gagal diproses")
                        }
                    }
            }
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }
}