package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListContactChatBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListReportBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin
import com.example.aplikasimonitoringdanevaluasi.model.Report

class ListReportAdminAdapter : RecyclerView.Adapter<ListReportAdminAdapter.ViewHolder>() {

    private val data = ArrayList<Report>()
    var onItemClick: ((Report) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Report>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Report) {
            binding.apply {
                tvReportTittle.text = data.tittle
                tvReportDate.text = data.date
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}