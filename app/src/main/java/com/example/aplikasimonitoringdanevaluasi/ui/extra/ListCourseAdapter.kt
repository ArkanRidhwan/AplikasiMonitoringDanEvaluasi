package com.example.aplikasimonitoringdanevaluasi.ui.extra

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListCourseBinding
import com.example.aplikasimonitoringdanevaluasi.model.Course

class ListCourseAdapter : RecyclerView.Adapter<ListCourseAdapter.ViewHolder>() {

    private val data = ArrayList<Course>()
    var onItemClick: ((Course) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Course>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Course) {
            binding.apply {
                tvCourseTittle.text = data.tittle
                tvCourseNumber.text = data.number
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListCourseBinding.inflate(
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