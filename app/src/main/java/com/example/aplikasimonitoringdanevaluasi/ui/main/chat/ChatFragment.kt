package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentChatBinding
import com.example.aplikasimonitoringdanevaluasi.model.Chat
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance
import com.example.aplikasimonitoringdanevaluasi.utils.showToast

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private val args: ChatFragmentArgs by navArgs()
    private val chatViewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ivChatBackButton.setOnClickListener {
                requireActivity().onBackPressed()
            }
            chatAdapter = ChatAdapter()
            rvChat.adapter = chatAdapter
            tvChatPersonName.text = args.receiverName
            if (args.image.isEmpty()) {
                ivChatProfileImage.setImageResource(R.drawable.ic_image_no_image)
            } else {
                Glide.with(requireContext())
                    .load(args.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                            .error(R.drawable.ic_image_error)
                    )
                    .into(ivChatProfileImage)
            }
            tvSendMessage.setOnClickListener {
                val message = edtMessage.text.toString()
                if (message.isNotEmpty()) {
                    val chat = Chat(
                        receiverId = args.receiverId,
                        receiverName = args.receiverName,
                        senderId = getInstance(requireContext()).getString(Constant.ID),
                        senderName = getInstance(requireContext()).getString(Constant.NAME),
                        message = message
                    )
                    chatViewModel.sendMessage(chat).observe(viewLifecycleOwner) {
                        if (it) {
                            binding.edtMessage.text.clear()
                            requireContext().showToast("Pesan Terkirim")
                        } else {
                            requireContext().showToast("Pesan Gagal Terkirim")
                        }
                    }
                } else {
                    requireActivity().showToast("Pesan masih kosong")
                }
            }
            loadChat()
        }
    }

    override fun onResume() {
        super.onResume()
        loadChat()
    }

    private fun loadChat() {
        val senderId = getInstance(requireContext()).getString(Constant.ID)
        val receiverId = args.receiverId
        chatViewModel.getMessage(senderId, receiverId).observe(viewLifecycleOwner) { listChat ->
            if (listChat != null) {
                chatAdapter.setListData(listChat)
            }
        }
    }
}