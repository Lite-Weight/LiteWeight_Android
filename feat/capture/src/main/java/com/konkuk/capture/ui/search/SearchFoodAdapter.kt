package com.konkuk.capture.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.konkuk.capture.R
import com.konkuk.capture.databinding.ItemSearchedFoodBinding
import com.konkuk.common.data.FoodInfo

class SearchFoodAdapter(private val onClick: (FoodInfo) -> Unit) :
    PagingDataAdapter<FoodInfo, SearchFoodAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemSearchedFoodBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FoodInfo) {
            binding.item = item
            binding.root.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_searched_food,
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FoodInfo>() {
            override fun areItemsTheSame(oldItem: FoodInfo, newItem: FoodInfo): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: FoodInfo, newItem: FoodInfo): Boolean {
                return oldItem == newItem
            }
        }
    }
}
