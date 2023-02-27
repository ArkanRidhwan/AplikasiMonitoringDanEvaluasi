package com.example.aplikasimonitoringdanevaluasi.ui.admin.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListStudentAdminBinding
import com.example.aplikasimonitoringdanevaluasi.model.Student

class HomeAdminAdapter : RecyclerView.Adapter<HomeAdminAdapter.ViewHolder>() {
    private val data = ArrayList<Student>()
    var onItemClick: ((Student) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Student>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListStudentAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Student) {
            binding.apply {
                tvStudentName.text = data.name
                if (data.companyName.isEmpty()) {
                    tvStudentCompany.text = "Lamaran perusahaan belum diterima"
                } else {
                    tvStudentCompany.text = data.companyName
                }
                if (data.image.isEmpty()) {
                    ivStudentPicture.setImageResource(R.drawable.ic_image_no_image)
                } else {
                    Glide.with(itemView.context)
                        .load(data.image)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivStudentPicture)
                }
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListStudentAdminBinding.inflate(
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