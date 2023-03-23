package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentListVideoBinding
import com.example.aplikasimonitoringdanevaluasi.ui.admin.course.CourseAdminFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.ui.student.course.CourseStudentFragmentDirections
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible


class ListVideoFragment : Fragment() {

    private lateinit var binding: FragmentListVideoBinding
    private lateinit var listVideoAdapter: ListVideoAdapter
    private val listVideoViewModel: ListVideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val role = getInstance(requireContext()).getString(Constant.ROLE)
        listVideoAdapter = ListVideoAdapter()
        binding.apply {
            if (role == getString(R.string.student)) {
                fabAddVideo.gone()
            }

            fabAddVideo.setOnClickListener {
                val action =
                    CourseAdminFragmentDirections.actionCourseAdminFragmentToUploadVideoFragment(null)
                findNavController().navigate(action)
            }

            listVideoAdapter.onItemClick = {
                if (role == getString(R.string.admin)) {
                    val action =
                        CourseAdminFragmentDirections.actionCourseAdminFragmentToWatchVideoFragment(
                            it
                        )
                    findNavController().navigate(action)
                } else {
                    val action =
                        CourseStudentFragmentDirections.actionCourseStudentFragmentToWatchVideoFragment(
                            it
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadVideo()
    }

    private fun loadVideo() {
        binding.apply {
            recyclerView.adapter = listVideoAdapter
            progressBar.visible()
            recyclerView.gone()
            listVideoViewModel.getAllVideo().observe(viewLifecycleOwner) {
                if (it?.isNotEmpty() == true) {
                    listVideoAdapter.setListData(it)
                    progressBar.gone()
                    recyclerView.visible()
                } else {
                    progressBar.gone()
                }
            }
        }
    }
}