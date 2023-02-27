package com.example.aplikasimonitoringdanevaluasi.ui.main.logbook

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasimonitoringdanevaluasi.databinding.LayoutListLogbookBinding
import com.example.aplikasimonitoringdanevaluasi.model.Logbook
import com.example.aplikasimonitoringdanevaluasi.utils.visible

class ListLogbookAdapter : RecyclerView.Adapter<ListLogbookAdapter.ViewHolder>() {
    private val data = ArrayList<Logbook>()
    var onItemClick: ((Logbook) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListData(listData: List<Logbook>?) {
        if (listData == null) return
        data.clear()
        data.addAll(listData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutListLogbookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Logbook) {
            binding.apply {
                tvLogbookDate.text = data.date
                tvLogbookContent.text = data.content
                when (data.status) {
                    "1" -> {
                        ivStatusProcessed.visible()
                    }
                    "2" -> {
                        ivStatusRejected.visible()
                    }
                    else -> {
                        ivStatusAccepted.visible()
                    }
                }

                itemView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LayoutListLogbookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}