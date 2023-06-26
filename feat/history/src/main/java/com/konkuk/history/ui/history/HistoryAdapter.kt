package com.konkuk.history.ui.history

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.common.Extension.toDate
import com.konkuk.history.databinding.HistoryItemBinding
import com.konkuk.history.domain.model.HistoryItemModel

class HistoryAdapter(
    private val onClick: (HistoryItemModel) -> Unit,
) : ListAdapter<HistoryItemModel, HistoryAdapter.ViewHolder>(diffUtil) {

    private val colorList: List<String> =
        listOf("#1C6BA4", "#F1E6EA", "#FAF0DB") // 파랑, 노랑, 초록 순서대로 색상 목록 생성

    inner class ViewHolder(val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(data: HistoryItemModel) = with(binding) {
            root.setOnClickListener {
                onClick(data)
            }
            val colorIndex = adapterPosition % colorList.size // 아이템의 위치에 따라 색상 목록의 인덱스 계산
            val itemColor = colorList[colorIndex] // 해당 인덱스의 색상 선택

            HistoryItem.backgroundTintList = ColorStateList.valueOf(Color.parseColor(itemColor))
            if (colorIndex == 0) {
                HistoryTime.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        com.konkuk.common.R.color.white,
                    ),
                )
                HistoryName.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        com.konkuk.common.R.color.white,
                    ),
                )
                HistoryCarlorie.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        com.konkuk.common.R.color.white,
                    ),
                )
            }

            timeLine.text = data.date.toDate("HH") + "시" // data.date.toString() + "PM"
            HistoryTime.text = data.date.toDate("HH:mm a")
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
