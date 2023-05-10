package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListChatBinding
import com.example.aplikasimonitoringdanevaluasi.model.Chat
import com.example.aplikasimonitoringdanevaluasi.utils.Constant
import com.example.aplikasimonitoringdanevaluasi.utils.getInstance

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val data = ArrayList<Chat>()
    var onItemClick: ((Chat) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Chat>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListChatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Chat) {
            binding.apply {
                tvSenderChat.text = data.senderName
                tvContentChat.text = data.message
                if (getInstance(itemView.context).getString(Constant.ID) == data.senderId) {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.white))
                } else {
                    cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red_Chat))
                }

                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}