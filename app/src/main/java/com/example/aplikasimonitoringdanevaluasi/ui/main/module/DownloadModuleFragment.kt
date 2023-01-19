package com.example.aplikasimonitoringdanevaluasi.ui.main.module

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentDownloadModuleBinding


class DownloadModuleFragment : Fragment() {

    private lateinit var binding: FragmentDownloadModuleBinding
    private val args: DownloadModuleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadModuleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvModuleTittle.text = args.module.tittle
            tvModuleContent.text = args.module.description
            tvModuleDate.text = args.module.date

            ivImageDownload.setOnClickListener {
                val url = args.module.link
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }
    }
}