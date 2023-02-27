package com.example.aplikasimonitoringdanevaluasi.ui.student.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListCompanyBinding
import com.example.aplikasimonitoringdanevaluasi.model.Company

class ListCompanyAdapter : RecyclerView.Adapter<ListCompanyAdapter.ViewHolder>() {
    private val data = ArrayList<Company>()
    var onItemClick: ((Company) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Company>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListCompanyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Company) {
            binding.apply {
                tvNameCompany.text = data.companyName
                if (data.image.isEmpty()) {
                    ivProfileCompany.setImageResource(R.drawable.ic_image_no_image)
                } else {
                    Glide.with(itemView.context)
                        .load(data.image)
                        .apply(
                            RequestOptions.placeholderOf(R.drawable.ic_image_loading)
                                .error(R.drawable.ic_image_error)
                        )
                        .into(ivProfileCompany)
                }
                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListCompanyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}