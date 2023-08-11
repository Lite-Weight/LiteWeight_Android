package com.konkuk.history.ui.history.staticsView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.history.databinding.StaticsItemBinding
import com.konkuk.history.domain.model.HistoryItemModel
import com.konkuk.history.ui.history.HistoryAdapter.Companion.diffUtil

class StaticsAdapter(
    private val onClick: (HistoryItemModel) -> Unit,
) : ListAdapter<HistoryItemModel, StaticsAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: StaticsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun bind(data: HistoryItemModel) = with(binding) {
            root.setOnClickListener {
                onClick(data)
            }
            // 영양성분 이름
            txtItemName.text = data.calories.toString() + "kcal"

            // 평균(csv파싱)
            progressBar.progress = data.calories / 2000 * 100
            txtAvr.text = data.calories.toString() + "kcal"
            // 사용자가 섭취한 영양성분
            progressBarPersonal.progress = data.calories / 2000 * 100
            txtPersonal.text = data.calories.toString() + "kcal"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StaticsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    /*  companion object {
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
      }*/
}
