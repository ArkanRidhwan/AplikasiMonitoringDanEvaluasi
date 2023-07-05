package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailStudentAdminBinding
import com.example.aplikasimonitoringdanevaluasi.utils.showToast


class DetailStudentAdminFragment : Fragment(), PopUpDeleteStudentFragment.UpdateData {

    private lateinit var binding: FragmentDetailStudentAdminBinding
    private val detailStudentAdminViewModel: DetailStudentAdminViewModel by viewModels()
    private val args: DetailStudentAdminFragmentArgs by navArgs()
    private var requestStudentId = ""
    private lateinit var mOptionDialogFragmentDeleteStudent: PopUpDeleteStudentFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailStudentAdminBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

            tvStudentName.text = args.student.name
            tvStudentNameEmail.text = args.student.email
            tvStudentClassName.text = args.student.className
            tvStudentCompanyName.text = args.student.companyName
            tvStudentJob.text = args.student.job
            tvStudentSchoolMajor.text = args.student.studentMajor
            tvStudentPhoneNumber.text = args.student.phoneNumber
            if (args.student.image.isEmpty()) {
                ivProfilePicture.setImageResource(R.drawable.ic_image_no_image)
            } else {
                Glide.with(requireContext())
                    .load(args.student.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                            .error(R.drawable.ic_image_error)
                    )
                    .into(ivProfilePicture)
            }

            detailStudentAdminViewModel.getRequestStudentById(args.student.id).observe(viewLifecycleOwner) {
                requestStudentId = it?.id.toString()
            }

            btnLogbook.setOnClickListener {
                val action =
                    DetailStudentAdminFragmentDirections.actionDetailStudentAdminFragmentToListLogbookFragment(
                        args.student.id
                    )
                findNavController().navigate(action)
            }

            btnLaporan.setOnClickListener {
                val action =
                    DetailStudentAdminFragmentDirections.actionDetailStudentAdminFragmentToListReportAdminFragment(args.student.id)
                findNavController().navigate(action)
            }

            btnDelete.setOnClickListener {
                mOptionDialogFragmentDeleteStudent = PopUpDeleteStudentFragment(this@DetailStudentAdminFragment)
                val mFragmentManager = childFragmentManager
                mOptionDialogFragmentDeleteStudent.show(
                    mFragmentManager,
                    PopUpDeleteStudentFragment::class.java.simpleName
                )

                /*detailStudentAdminViewModel.deleteStudent(args.student.email).observe(viewLifecycleOwner) {
                    if (it == true) {
                        requireActivity().onBackPressed()
                        requireContext().showToast("Akun siswa berhasil dihapus")
                    } else {
                        requireContext().showToast("Akun siswa gagal dihapus")
                    }
                }*/
            }
        }
    }
    fun deleteAccount (){
        detailStudentAdminViewModel.deleteStudent(args.student.email).observe(viewLifecycleOwner) {
            if (it == true) {
                requireActivity().onBackPressed()
                requireContext().showToast("Akun siswa berhasil dihapus")
            } else {
                requireContext().showToast("Akun siswa gagal dihapus")
            }
        }
    }

    override fun setDataUpdate(status: Boolean) {
        if (status) {
            deleteAccount()
        }
    }
}