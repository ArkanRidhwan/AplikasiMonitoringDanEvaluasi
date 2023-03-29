package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDetailCourseBinding
import com.example.aplikasimonitoringdanevaluasi.model.Course
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible

class DetailCourseFragment : Fragment() {

    private lateinit var binding: FragmentDetailCourseBinding
    private val args: DetailCourseFragmentArgs by navArgs()
    private val detailCourseViewModel: DetailCourseViewModel by viewModels()
    var check1: Boolean = false
    var check2: Boolean = false
    var check3: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCourseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            tvTittleCourse.text = args.course.tittle

            /*detailCourseViewModel.getCourseById(args.course.id).observe(viewLifecycleOwner) {
                if (it?.check1 == true && it.check2 && it.check3) {
                    btnDone.visible()
                    btnNotDone.gone()
                } else {
                    btnDone.gone()
                    btnNotDone.visible()
                }
            }*/

            cvVideo.setOnClickListener {
                detailCourseViewModel.getVideoById(args.course.id).observe(viewLifecycleOwner) {
                    if (it?.courseId != null) {
                        val action =
                            DetailCourseFragmentDirections.actionDetailCourseFragmentToWatchVideoFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                }

                /*detailCourseViewModel.getCourseById(args.course.id).observe(viewLifecycleOwner) {
                    if (it != null) {
                        check2 = it.check2
                        check3 = it.check3
                    }


                    val course = Course(
                        id = args.course.id,
                        tittle = args.course.tittle,
                        number = args.course.number,
                        timestamp = args.course.timestamp,
                        check1 = true,
                        check2 = check2,
                        check3 = check3
                    )

                    detailCourseViewModel.updateCourseById(course, args.course.id)
                        .observe(viewLifecycleOwner) {
                        }
                }*/
            }

            cvModule.setOnClickListener {
                detailCourseViewModel.getModuleById(args.course.id).observe(viewLifecycleOwner) {
                    if (it?.courseId != null) {
                        val action =
                            DetailCourseFragmentDirections.actionDetailCourseFragmentToDownloadModuleFragment(
                                it
                            )
                        findNavController().navigate(action)
                    }
                }
                /*detailCourseViewModel.getCourseById(args.course.id).observe(viewLifecycleOwner) {
                    if (it != null) {
                        check1 = it.check1
                        check3 = it.check3
                    }


                    val course = Course(
                        id = args.course.id,
                        tittle = args.course.tittle,
                        number = args.course.number,
                        timestamp = args.course.timestamp,
                        check1 = check1,
                        check2 = true,
                        check3 = check3
                    )

                    detailCourseViewModel.updateCourseById(course, args.course.id)
                        .observe(viewLifecycleOwner) {
                        }
                }*/
            }

            cvEvaluationLink.setOnClickListener {
                detailCourseViewModel.getEvaluationById(args.course.id)
                    .observe(viewLifecycleOwner) {
                        if (it?.courseId != null) {
                            val url = it.link
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(url)
                            startActivity(i)
                        }
                    }

                /*detailCourseViewModel.getCourseById(args.course.id).observe(viewLifecycleOwner) {
                    if (it != null) {
                        check1 = it.check1
                        check2 = it.check2
                    }


                    val course = Course(
                        id = args.course.id,
                        tittle = args.course.tittle,
                        number = args.course.number,
                        timestamp = args.course.timestamp,
                        check1 = check1,
                        check2 = check2,
                        check3 = true
                    )

                    detailCourseViewModel.updateCourseById(course, args.course.id)
                        .observe(viewLifecycleOwner) {
                        }
                }*/
            }

            /*btnDone.setOnClickListener {
                detailCourseViewModel.getCourseById(args.course.id).observe(viewLifecycleOwner) {
                    if (it != null) {
                        check1 = it.check1
                        check2 = it.check2
                        check3 = it.check3
                    }

                    val course = Course(
                        id = args.course.id,
                        tittle = args.course.tittle,
                        number = args.course.number,
                        timestamp = args.course.timestamp,
                        check1 = check1,
                        check2 = check2,
                        check3 = check3,
                        finalCheck = true
                    )

                    detailCourseViewModel.updateCourseById(course, args.course.id)
                        .observe(viewLifecycleOwner) {
                        }
                }
                requireActivity().onBackPressed()
            }*/
        }
    }
}