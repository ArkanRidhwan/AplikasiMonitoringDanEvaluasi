package com.example.aplikasimonitoringdanevaluasi.ui.main.chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListChatStudentBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin

class ListAdminAdapter : RecyclerView.Adapter<ListAdminAdapter.ViewHolder>() {

    private val data = ArrayList<Admin>()
    var onItemClick: ((Admin) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Admin>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListChatStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Admin) {
            binding.apply {
                tvChatListStudentName.text = data.name
                tvChatListStudentCompanyName.text = data.email
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListChatStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}