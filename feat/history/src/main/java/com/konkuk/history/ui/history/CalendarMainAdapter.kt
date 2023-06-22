package com.konkuk.history.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.common.R
import com.konkuk.history.databinding.CalendarItemBinding
import com.konkuk.history.domain.model.HistoryCalendarModel

class CalendarMainAdapter(
    private val items: ArrayList<HistoryCalendarModel>,
    private val onClick: (HistoryCalendarModel) -> Unit,
) : RecyclerView.Adapter<CalendarMainAdapter.ViewHolder>() {

    //    private var selectedItemPosition = RecyclerView.SCROLLBAR_POSITION_LEFT
    private var selectedItemPosition = RecyclerView.SCROLLBAR_POSITION_DEFAULT

    inner class ViewHolder(val binding: CalendarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: HistoryCalendarModel) = with(binding) {
            root.setOnClickListener {
                onClick(data)
            }

            tvDateCalendarItem.text = data.date
            tvDayCalendarItem.text = data.day

            if (adapterPosition == selectedItemPosition) {
                clCalendarItem.isSelected = true
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
                clCalendarItem.isSelected = false
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

            clCalendarItem.setOnClickListener {
                // 기존에 선택된 아이템의 선택 상태 해제
                if (selectedItemPosition != RecyclerView.NO_POSITION) {
                    notifyItemChanged(selectedItemPosition)
                }

                // 현재 아이템 선택
                selectedItemPosition = adapterPosition
                notifyItemChanged(selectedItemPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            CalendarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
