package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentWatchVideoBinding
import com.example.aplikasimonitoringdanevaluasi.utils.gone
import com.example.aplikasimonitoringdanevaluasi.utils.visible
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer


class WatchVideoFragment : Fragment() {

    private lateinit var binding: FragmentWatchVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchVideoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            /*val player = ExoPlayer.Builder(requireActivity()).build()
            videoView.player = player*/

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
            val mediaItem = MediaItem.fromUri("https://www.youtube.com/watch?v=ciYgd5Hj2-0")
            simpleExoPlayer.setMediaItem(mediaItem)
            simpleExoPlayer.prepare()
            simpleExoPlayer.play()
        }
    }
}