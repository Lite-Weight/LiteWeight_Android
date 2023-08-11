package com.konkuk.history.ui.history.staticsView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.history.databinding.StaticsItemBinding
import java.lang.Math.abs

class StaticsAdapter(
    var items: ArrayList<StaticsItemModel>,
) : RecyclerView.Adapter<StaticsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: StaticsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: StaticsItemModel) = with(binding) {
            /* root.setOnClickListener {
                 onClick(data)
             }*/
            // 영양성분 이름
            txtItemName.text = data.itemName

            // 평균(csv파싱)
            progressBar.progress = 100
            txtAvr.text = data.avgCalorie.toString() + data.foodUnit
            // 사용자가 섭취한 영양성분
            progressBarPersonal.progress =
                (data.myCalorie * 100 / data.avgCalorie).toInt()
            txtPersonal.text = data.myCalorie.toString() + data.foodUnit
            txtImEat.text = "내가 먹은 ${data.itemName}"
            // About
            val calculate = data.avgCalorie - data.myCalorie
            if (calculate < 0) {
                txtAbout.text = "${data.avgAge}살 남성에 비해 ${abs(calculate)}${data.foodUnit} 더 먹었어요"
            } else {
                txtAbout.text = "${data.avgAge}살 남성에 비해 ${calculate}${data.foodUnit} 덜 먹었어요"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StaticsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
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
