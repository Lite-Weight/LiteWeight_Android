package com.konkuk.history.ui.history.statistic

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.common.ui.decoration.AnimateProgressBarCommon
import com.konkuk.history.databinding.StaticsItemBinding
import java.lang.Math.abs

class StaticsAdapter : ListAdapter<StaticsItemModel, StaticsAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(val binding: StaticsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(data: StaticsItemModel) = with(binding) {
            // 영양성분 이름
            txtItemName.text = data.itemName

            // 평균(csv파싱)
            progressBar.progress = 100
            txtAvr.text = data.avgCalorie.toString() + data.foodUnit

            // 사용자가 섭취한 영양성분
            progressBarPersonal.progress =
                if (data.avgCalorie == 0) 100 else (data.myCalorie * 100 / data.avgCalorie)
            txtPersonal.text = data.myCalorie.toString() + data.foodUnit
            txtImEat.text = "내가 먹은 ${data.itemName}"

            // About
            val calculate = data.avgCalorie - data.myCalorie
            txtAbout.text =
                "${data.avgAge}살 ${if (data.gender == GENDER.MALE) "남성" else "여성"}에 비해 " +
                "${abs(calculate)}${data.foodUnit} ${if (calculate < 0) "더" else "덜"} 먹었어요"

            animateProgressBar(
                progressBarPersonal,
                if (data.avgCalorie == 0) 100 else (data.myCalorie * 100 / data.avgCalorie),
            )
        }
    }

    private fun animateProgressBar(progressBar: ProgressBar, progress: Int) {
        val anim = AnimateProgressBarCommon(progressBar, 0f, progress.toFloat())
        anim.duration = 1800
        progressBar.startAnimation(anim)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StaticsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<StaticsItemModel>() {
            override fun areItemsTheSame(
                oldItem: StaticsItemModel,
                newItem: StaticsItemModel,
            ): Boolean {
                return oldItem.itemName == newItem.itemName
            }

            override fun areContentsTheSame(
                oldItem: StaticsItemModel,
                newItem: StaticsItemModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
