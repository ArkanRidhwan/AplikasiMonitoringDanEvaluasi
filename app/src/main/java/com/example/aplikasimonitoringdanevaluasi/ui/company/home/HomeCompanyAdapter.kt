package com.example.aplikasimonitoringdanevaluasi.ui.company.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListStudentCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListStudentRequestBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent

class HomeCompanyAdapter : RecyclerView.Adapter<HomeCompanyAdapter.ViewHolder>() {

    private val data = ArrayList<RequestStudent>()
    var onItemClick: ((RequestStudent) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<RequestStudent>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: LayoutListStudentCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RequestStudent) {
            binding.apply {
                tvStudentName.text = data.studentName
                tvStudentEmail.text = data.studentEmail
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListStudentCompanyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}