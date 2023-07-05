package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentAddCourseBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.PopUpDeleteCourseFragment
import com.example.aplikasimonitoringdanevaluasi.ui.admin.home.PopUpDeleteStudentFragment
import com.example.aplikasimonitoringdanevaluasi.utils.showToast


class AddCourseFragment : Fragment(), PopUpDeleteCourseFragment.UpdateData {

    private lateinit var binding: FragmentAddCourseBinding
    private val addCourseViewModel: AddCourseViewModel by viewModels()
    private val args: AddCourseFragmentArgs by navArgs()
    private lateinit var mOptionDialogFragmentDeleteCourse: PopUpDeleteCourseFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCourseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvBartittle.text = args.course.tittle
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            cvVideo.setOnClickListener {
                val action =
                    AddCourseFragmentDirections.actionAddCourseFragmentToUploadVideoFragment(
                        args.course.id
                    )
                findNavController().navigate(action)
            }

            cvModule.setOnClickListener {
                val action =
                    AddCourseFragmentDirections.actionAddCourseFragmentToUploadModuleFragment(
                        args.course.id
                    )
                findNavController().navigate(action)
            }

            cvEvaluationLink.setOnClickListener {
                val action =
                    AddCourseFragmentDirections.actionAddCourseFragmentToAddEvaluationLinkFragment(
                        args.course.id
                    )
                findNavController().navigate(action)
            }

            btnDelete.setOnClickListener {
                mOptionDialogFragmentDeleteCourse = PopUpDeleteCourseFragment(this@AddCourseFragment)
                val mFragmentManager = childFragmentManager
                mOptionDialogFragmentDeleteCourse.show(
                    mFragmentManager,
                    PopUpDeleteCourseFragment::class.java.simpleName
                )

                /*addCourseViewModel.deleteVideo(args.course.id).observe(viewLifecycleOwner) {
                    if (it == true) {
                        val action =
                            AddCourseFragmentDirections.actionAddCourseFragmentToListCourseAdminFragment()
                        findNavController().navigate(action)
                        requireContext().showToast("Materi bab ini berhasil dihapus")
                    } else {
                        requireContext().showToast("Materi bab ini gagal dihapus")
                    }
                }*/
            }
        }
    }
    fun deleteCourse (){
        addCourseViewModel.deleteVideo(args.course.id).observe(viewLifecycleOwner) {
            if (it == true) {
                val action =
                    AddCourseFragmentDirections.actionAddCourseFragmentToListCourseAdminFragment()
                findNavController().navigate(action)
                requireContext().showToast("Materi berhasil dihapus")
            } else {
                requireContext().showToast("Materi gagal dihapus")
            }
        }
    }

    override fun setDataUpdate(status: Boolean) {
        if (status) {
            deleteCourse()
        }
    }
}