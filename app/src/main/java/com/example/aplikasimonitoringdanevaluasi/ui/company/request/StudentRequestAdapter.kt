package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListStudentRequestBinding
import com.example.aplikasimonitoringdanevaluasi.model.Admin

class StudentRequestAdapter : RecyclerView.Adapter<StudentRequestAdapter.ViewHolder>() {

    private val data = ArrayList<Admin>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Admin>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: LayoutListStudentRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Admin) {
            binding.apply {
                tvStudentName.text = data.name
                tvStudentEmail.text = data.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListStudentRequestBinding.inflate(
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