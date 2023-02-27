package com.example.aplikasimonitoringdanevaluasi.ui.company.request

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListLogbookRequestBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook

class StudentLogbookRequestAdapter :
    RecyclerView.Adapter<StudentLogbookRequestAdapter.ViewHolder>() {

    private val data = ArrayList<Logbook>()
    var onItemClick: ((Logbook) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Logbook>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListLogbookRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Logbook) {
            binding.apply {
                tvStudentName.text = data.name
                tvStudentLogbookDate.text = data.date
                tvContent.text = data.content
                if (data.image.isEmpty()) {
                    ivProfileLogbook.setImageResource(R.drawable.ic_image_no_image)
                } else {
                    Glide.with(itemView.context)
                        .load(data.image)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivProfileLogbook)
                }
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListLogbookRequestBinding.inflate(
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