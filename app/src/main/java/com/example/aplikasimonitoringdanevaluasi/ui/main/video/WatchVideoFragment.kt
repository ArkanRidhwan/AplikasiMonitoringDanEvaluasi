package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentWatchVideoBinding
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class WatchVideoFragment : Fragment() {

    private lateinit var binding: FragmentWatchVideoBinding
    private val database = Firebase.database
    private val collVideo = database.getReference(Constant.COLL_VIDEO)
    private val args: WatchVideoFragmentArgs by navArgs()
    var isFullScreen = false
    var isLockScreen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            val role = getInstance(requireContext()).getString(Constant.ROLE)
            if (role == getString(R.string.student))
                btnDelete.gone()
            else
                btnDelete.visible()

            tvVideoTittle.text = args.video.tittle
            tvVideoContent.text = args.video.description
            tvVideoDate.text = args.video.date

            val simpleExoPlayer = SimpleExoPlayer.Builder(requireContext())
                .setSeekBackIncrementMs(10000)
                .setSeekForwardIncrementMs(10000)
                .build()
            videoView.player = simpleExoPlayer
            videoView.keepScreenOn = true
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
            val mediaItem = MediaItem.fromUri(args.video.link)
            simpleExoPlayer.setMediaItem(mediaItem)
            simpleExoPlayer.prepare()
            simpleExoPlayer.play()

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
                    ivBar.gone()
                    tvBartittle.gone()
                    ivBack.gone()
                    btnDelete.gone()
                    scrollViewLayout.gone()
                    videoView.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                } else {
                    btn_fullscreen.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_fullscreen_enter
                        )
                    )
                    requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    ivBar.visible()
                    tvBartittle.visible()
                    ivBack.visible()
                    btnDelete.visible()
                    scrollViewLayout.visible()
                    if (role == getString(R.string.student))
                        binding.btnDelete.gone()
                    else
                        binding.btnDelete.visible()
                    videoView.layoutParams.height = 650
                }
                isFullScreen = !isFullScreen
            }

            //Make LockScreen
            val btn_lockscreen = view.findViewById<ImageView>(R.id.exo_lock)
            btn_lockscreen.setOnClickListener {
                if (!isLockScreen) {
                    btn_lockscreen.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity().applicationContext,
                            R.drawable.ic_lock_close
                        )
                    )
                } else {
                    btn_lockscreen.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity().applicationContext,
                            R.drawable.ic_lock_open
                        )
                    )
                }
                isLockScreen = !isLockScreen
                lockScreen(isLockScreen)
            }

            btnDelete.setOnClickListener {
                //Loading nanti
                collVideo.child(args.video.id)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (i in snapshot.children) {
                                i.ref.removeValue()
                            }
                            requireActivity().showToast("Berhasil")
                            requireActivity().onBackPressed()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            requireActivity().showToast("Gagal")
                        }
                    })
            }
        }
    }

    private fun lockScreen(lockScreen: Boolean) {
        val sec_mid = view?.findViewById<LinearLayout>(R.id.sec_controlVid1)
        val sec_bottom = view?.findViewById<LinearLayout>(R.id.sec_controlVid2)
        if (lockScreen) {
            sec_mid?.gone()
            sec_bottom?.gone()
        } else {
            sec_mid?.visible()
            sec_bottom?.visible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.videoView.player?.stop()
        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }
}
