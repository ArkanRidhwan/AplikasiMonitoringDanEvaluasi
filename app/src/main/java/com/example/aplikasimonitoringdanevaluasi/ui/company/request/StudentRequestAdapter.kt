package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListStudentRequestBinding
import com.example.aplikasimonitoringdanevaluasi.model.RequestStudent
import com.example.aplikasimonitoringdanevaluasi.utils.loadCircleImageFromUrl

class StudentRequestAdapter : RecyclerView.Adapter<StudentRequestAdapter.ViewHolder>() {

    private val data = ArrayList<RequestStudent>()
    var onItemClick: ((RequestStudent) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<RequestStudent>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListStudentRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RequestStudent) {
            binding.apply {
                tvStudentName.text = data.studentName
                tvStudentEmail.text = data.studentEmail
                if (data.image.isEmpty()) {
                    ivProfileStudent.setImageResource(R.drawable.img_no_image)
                } else {
                    ivProfileStudent.loadCircleImageFromUrl(data.image)
                }
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
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