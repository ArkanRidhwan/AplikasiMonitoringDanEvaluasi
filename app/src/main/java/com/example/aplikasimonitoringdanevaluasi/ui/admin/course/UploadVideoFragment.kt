package com.example.aplikasimonitoringdanevaluasi.ui.admin.course

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
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentUploadVideoBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutCustomControllerBinding
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


class UploadVideoFragment : Fragment() {

    var videoUri: Uri? = null
    var isFullScreen = false
    var isLockScreen = false

    private lateinit var binding: FragmentUploadVideoBinding
    private lateinit var file: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Make FullScreen
        val btn_fullscreen = view.findViewById<ImageView>(R.id.btn_fullscreen)
        btn_fullscreen.setOnClickListener {
            if (!isFullScreen) {
                btn_fullscreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_fullscreen_exit
                    )
                )
                requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                binding.ivBar.gone()
                binding.tvBartittle.gone()
                binding.ivBack.gone()
                binding.constraintLayout2.gone()
            } else {
                btn_fullscreen.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_fullscreen_enter
                    )
                )
                requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                binding.ivBar.visible()
                binding.tvBartittle.visible()
                binding.ivBack.visible()
                binding.constraintLayout2.visible()
            }
            isFullScreen = !isFullScreen
        }

        //Make LockScreen
        val btn_lockscreen = view.findViewById<ImageView>(R.id.exo_lock)
        btn_lockscreen.setOnClickListener {
            if(!isLockScreen){
                btn_lockscreen.setImageDrawable(ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.ic_lock_close))
            } else {
                btn_lockscreen.setImageDrawable(ContextCompat.getDrawable(requireActivity().applicationContext, R.drawable.ic_lock_open))
            }
            isLockScreen = !isLockScreen
            lockScreen(isLockScreen)
        }

        binding.apply {
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
                            requireContext().showToast("Upload Video Failed!")
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
                            requireContext().showToast("Upload Video Success!")

                            task.storage.downloadUrl.addOnSuccessListener { url ->
                                Log.d(TAG, "downloadUri: $url")
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
        val sec_mid = view?.findViewById<LinearLayout>(R.id.sec_controlVid1)
        val sec_bottom = view?.findViewById<LinearLayout>(R.id.sec_controlVid2)
        if(lockScreen){
            sec_mid?.gone()
            sec_bottom?.gone()
        } else {
            sec_mid?.visible()
            sec_bottom?.visible()
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