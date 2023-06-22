package com.konkuk.history.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.history.databinding.HistoryItemBinding
import com.konkuk.history.domain.model.HistoryItemModel

class HistoryAdapter(
    private val items: ArrayList<HistoryItemModel>,
    private val onClick: (HistoryItemModel) -> Unit,
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: HistoryItemModel) = with(binding) {
            root.setOnClickListener {
                onClick(data)
            }

            HistoryTime.text = data.date.toString()
            HistoryName.text = data.name
            HistoryCarlorie.text = data.calories.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
