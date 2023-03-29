package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentUploadVideoBinding
import com.example.aplikasimonitoringdanevaluasi.model.Video
import com.example.aplikasimonitoringdanevaluasi.ui.extra.AddCourseFragmentArgs
import com.example.aplikasimonitoringdanevaluasi.utils.getDateNow
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.showToast
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*


class UploadVideoFragment : Fragment() {

    private var videoUri: Uri? = null
    private var isFullScreen = false
    private var isLockScreen = false
    private var urlDownload: String? = ""

    private lateinit var binding: FragmentUploadVideoBinding
    private lateinit var file: File
    private val uploadVideoViewModel: UploadVideoViewModel by viewModels()
    private val args: UploadVideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {

            //Make FullScreen
            val btnFullscreen = view.findViewById<ImageView>(R.id.btn_fullscreen)
            btnFullscreen.setOnClickListener {
                if (!isFullScreen) {
                    btnFullscreen.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_fullscreen_exit
                        )
                    )
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    ivBar.gone()
                    tvBartittle.gone()
                    ivBack.gone()
                    scrollViewLayout.gone()
                    vidPreview.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                } else {
                    btnFullscreen.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_fullscreen_enter
                        )
                    )
                    requireActivity().requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    ivBar.visible()
                    tvBartittle.visible()
                    ivBack.visible()
                    scrollViewLayout.visible()
                    scrollViewLayout.visible()
                    vidPreview.layoutParams.height = 650
                }
                isFullScreen = !isFullScreen
            }

            //Make LockScreen
            val btnLockscreen = view.findViewById<ImageView>(R.id.exo_lock)
            btnLockscreen.setOnClickListener {
                if (!isLockScreen) {
                    btnLockscreen.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity().applicationContext,
                            R.drawable.ic_lock_close
                        )
                    )
                } else {
                    btnLockscreen.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity().applicationContext,
                            R.drawable.ic_lock_open
                        )
                    )
                }
                isLockScreen = !isLockScreen
                lockScreen(isLockScreen)
            }

            ivBack.setOnClickListener {
                requireActivity().onBackPressed()
                /*val action =
                    UploadVideoFragmentDirections.actionUploadVideoFragmentToCourseAdminFragment()
                findNavController().navigate(action)*/
            }


            val startForVideoResult =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                    val resultCode = result.resultCode
                    val data = result.data
                    if (resultCode == AppCompatActivity.RESULT_OK && data?.data != null) {
                        videoUri = data.data
                        file = File(videoUri.toString())
                        btnUploadVideo.visible()
                        btnChangeContent.visible()

                        //Exoplayer
                        val simpleExoPlayer = SimpleExoPlayer.Builder(requireContext())
                            .setSeekBackIncrementMs(10000)
                            .setSeekForwardIncrementMs(10000)
                            .build()
                        vidPreview.player = simpleExoPlayer
                        vidPreview.keepScreenOn = true
                        simpleExoPlayer.addListener(object : Player.Listener {
                            override fun onPlayerStateChanged(
                                playWhenReady: Boolean,
                                playbackState: Int
                            ) {
                                if (playbackState == Player.STATE_BUFFERING) {
                                    progressBarVideo.visible()
                                } else if (playbackState == Player.STATE_READY) {
                                    progressBarVideo.gone()
                                }
                            }
                        })
                        val videoSource = Uri.parse(videoUri.toString())
                        val mediaItem = MediaItem.fromUri(videoSource)
                        simpleExoPlayer.setMediaItem(mediaItem)
                        simpleExoPlayer.prepare()
                        simpleExoPlayer.play()
                    }
                }

            ivChooseVideo.setOnClickListener {
                ivChooseVideo.gone()
                tvChooseVideo.gone()
                val intent = Intent().apply {
                    type = "video/*"
                    action = Intent.ACTION_GET_CONTENT
                }
                startForVideoResult.launch(intent)
            }

            btnChangeContent.setOnClickListener {
                ivChooseVideo.gone()
                tvChooseVideo.gone()
                vidPreview.player?.stop()
                val intent = Intent().apply {
                    type = "video/*"
                    action = Intent.ACTION_GET_CONTENT
                }
                startForVideoResult.launch(intent)
            }

            btnUploadVideo.setOnClickListener {
                val tittle = etUploadVideoTittle.text.toString()
                val description = etUploadVideoContent.text.toString()

                progressBarUpload.visible()
                tvProgress.visible()
                btnUploadVideo.gone()
                btnChangeContent.gone()
                tvUploadVideoTittle.gone()
                tvUploadVideoContent.gone()
                etUploadVideoContent.gone()
                etUploadVideoTittle.gone()
                val storageRef = Firebase.storage.reference
                val videosRef = storageRef.child("videos/${file.name}")
                videoUri?.let { uri ->
                    videosRef.putFile(uri)
                        .addOnFailureListener {
                            progressBarUpload.gone()
                            tvProgress.gone()
                            btnUploadVideo.visible()
                            btnChangeContent.visible()
                            tvUploadVideoTittle.visible()
                            tvUploadVideoContent.visible()
                            etUploadVideoContent.visible()
                            etUploadVideoTittle.visible()
                        }
                        .addOnSuccessListener { task ->
                            progressBarUpload.gone()
                            tvProgress.gone()
                            btnUploadVideo.visible()
                            btnChangeContent.visible()
                            tvUploadVideoTittle.visible()
                            tvUploadVideoContent.visible()
                            etUploadVideoContent.visible()
                            etUploadVideoTittle.visible()

                            task.storage.downloadUrl.addOnSuccessListener { url ->
                                Log.d(TAG, "downloadUri: $url")
                                urlDownload = url.toString()
                                val id = UUID.randomUUID().toString()
                                val video = Video(
                                    id = id,
                                    tittle = tittle,
                                    description = description,
                                    date = getDateNow(),
                                    timestamp = System.currentTimeMillis().toString(),
                                    courseId = args.tittleCourseId.toString(),
                                    link = urlDownload.toString(),
                                )
                                uploadVideoViewModel.saveVideo(video)
                                    .observe(viewLifecycleOwner) { data ->
                                        if (data == true) {
                                            requireActivity().onBackPressed()
                                            requireContext().showToast("Berhasil menyimpan video")
                                        } else {
                                            requireContext().showToast("Gagal menyimpan video")
                                        }
                                    }
                            }
                        }
                        .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                            val progress = (100.0 * bytesTransferred) / totalByteCount
                            tvProgress.text = "Uploading ${progress.toInt()} %"
                        }
                }
            }
        }
    }

    private fun lockScreen(lockScreen: Boolean) {
        val secMid = view?.findViewById<LinearLayout>(R.id.sec_controlVid1)
        val secBottom = view?.findViewById<LinearLayout>(R.id.sec_controlVid2)
        if (lockScreen) {
            secMid?.gone()
            secBottom?.gone()
        } else {
            secMid?.visible()
            secBottom?.visible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vidPreview.player?.stop()
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    companion object {
        private const val TAG = "StorageActivity"
    }
}