package com.konkuk.history.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.common.R
import com.konkuk.history.databinding.CalendarItemBinding
import com.konkuk.history.domain.model.HistoryCalendarModel

class CalendarMainAdapter(
    private val onClick: (HistoryCalendarModel) -> Unit,
) : ListAdapter<HistoryCalendarModel, CalendarMainAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: CalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryCalendarModel) = with(binding) {
            tvDateCalendarItem.text = data.date
            tvDayCalendarItem.text = data.day
            if (data.isSelected) {
                tvDateCalendarItem.text = data.date
                clCalendarItem.setBackgroundResource(com.konkuk.history.R.drawable.calendar_item_checked)
                tvDateCalendarItem.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white,
                    ),
                )
                tvDayCalendarItem.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white,
                    ),
                )
            } else {
                clCalendarItem.setBackgroundResource(com.konkuk.history.R.drawable.calendar_item_background)
                tvDateCalendarItem.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black,
                    ),
                )
                tvDayCalendarItem.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black,
                    ),
                )
            }

            root.setOnClickListener {
                onClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<HistoryCalendarModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryCalendarModel,
                newItem: HistoryCalendarModel,
            ): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(
                oldItem: HistoryCalendarModel,
                newItem: HistoryCalendarModel,
            ): Boolean {
                return oldItem.isSelected == newItem.isSelected
            }
        }
    }
}
