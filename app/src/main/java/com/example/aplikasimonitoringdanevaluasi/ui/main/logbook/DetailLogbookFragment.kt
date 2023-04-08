package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailLogbookBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class DetailLogbookFragment : Fragment() {

    private lateinit var binding: FragmentDetailLogbookBinding
    private val args: DetailLogbookFragmentArgs by navArgs()
    private val detailLogbookFragmentViewModel: DetailLogbookFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailLogbookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            val role = getInstance(requireContext()).getString(Constant.ROLE)

            if (role == getString(R.string.company) && args.logbook.status == "1") {
                btnAccept.visible()
                btnReject.visible()
            }

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

            btnReject.setOnClickListener {
                detailLogbookFragmentViewModel.updateLogbookStatus(
                    logbook,
                    args.logbook.id, "2"
                ).observe(viewLifecycleOwner) {
                    if (it == true) {
                        requireContext().showToast("Logbook berhasil diproses")
                        requireActivity().onBackPressed()
                    } else {
                        requireContext().showToast("Logbook gagal diproses")
                    }
                }
            }

            btnAccept.setOnClickListener {
                detailLogbookFragmentViewModel.updateLogbookStatus(
                    logbook,
                    args.logbook.id, "3"
                ).observe(viewLifecycleOwner) {
                    if (it == true) {
                        requireContext().showToast("Logbook berhasil diproses")
                        requireActivity().onBackPressed()
                    } else {
                        requireContext().showToast("Logbook gagal diproses")
                    }
                }
            }

            tvDate.text = "Dibuat pada: ${args.logbook.date}"
            tvContentLogbook.text = args.logbook.content
            tvActivityDate.text = args.logbook.activityDate
        }
    }
}