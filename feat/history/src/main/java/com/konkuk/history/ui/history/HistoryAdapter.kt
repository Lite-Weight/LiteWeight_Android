package com.konkuk.history.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.common.Extension.toDate
import com.konkuk.history.databinding.HistoryItemBinding
import com.konkuk.history.domain.model.HistoryItemModel

class HistoryAdapter(
    private val onClick: (HistoryItemModel) -> Unit,
) : ListAdapter<HistoryItemModel, HistoryAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: HistoryItemModel) = with(binding) {
            root.setOnClickListener {
                onClick(data)
            }

            timeLine.text = data.date.toDate("HH:mm") // data.date.toString() + "PM"
            HistoryTime.text = data.date.toString()
            HistoryName.text = data.name
            HistoryCarlorie.text = data.calories.toString() + "kcal"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<HistoryItemModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryItemModel,
                newItem: HistoryItemModel,
            ): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(
                oldItem: HistoryItemModel,
                newItem: HistoryItemModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
