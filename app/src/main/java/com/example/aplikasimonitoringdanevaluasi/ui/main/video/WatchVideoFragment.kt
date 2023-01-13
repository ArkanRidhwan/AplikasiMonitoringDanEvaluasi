package com.example.aplikasimonitoringdanevaluasi.ui.main.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentWatchVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


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
            val player = ExoPlayer.Builder(requireActivity()).build()
            videoView.player = player

            val mediaItem = let { MediaItem.fromUri("//www.youtube.com/watch?v=xEeFrLSkMm8")}
            if (mediaItem != null) {
                player.setMediaItem(mediaItem)
            }
            player.prepare()
        }
    }
}