package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentWatchVideoBinding
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer


class WatchVideoFragment : Fragment() {

    private lateinit var binding: FragmentWatchVideoBinding
    private val args: WatchVideoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
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
            //val videoSource = Uri.parse(videoUri.toString())
            val mediaItem = MediaItem.fromUri(args.video.link)
            simpleExoPlayer.setMediaItem(mediaItem)
            simpleExoPlayer.prepare()
            simpleExoPlayer.play()
        }
    }
}