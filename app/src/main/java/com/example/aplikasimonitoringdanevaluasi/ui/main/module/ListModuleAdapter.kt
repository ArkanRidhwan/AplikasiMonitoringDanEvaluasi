package com.example.aplikasimonitoringdanevaluasi.ui.main.module

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListModuleBinding
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListVideoBinding
import com.example.aplikasimonitoringdanevaluasi.model.Module
import com.example.aplikasimonitoringdanevaluasi.model.Video

class ListModuleAdapter : RecyclerView.Adapter<ListModuleAdapter.ViewHolder>() {
    private val data = ArrayList<Module>()
    var onItemClick: ((Module) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Module>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListModuleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Module) {
            binding.apply {
                tvTittle.text = data.tittle
                tvDate.text = data.date
                tvDescription.text = data.description
                /*Glide.with(itemView.context)
                    .load(data.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                            .error(R.drawable.ic_image_error)
                    )
                    .into(ivProfileCompany)*/
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListModuleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}