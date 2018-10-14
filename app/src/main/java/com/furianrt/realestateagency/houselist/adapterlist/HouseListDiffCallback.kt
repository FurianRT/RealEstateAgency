package com.furianrt.realestateagency.houselist.adapterlist

import androidx.recyclerview.widget.DiffUtil
import com.furianrt.realestateagency.data.model.House

class HouseListDiffCallback : DiffUtil.ItemCallback<House>() {

    override fun areContentsTheSame(oldItem: House, newItem: House): Boolean = oldItem == newItem

    override fun areItemsTheSame(oldItem: House, newItem: House): Boolean = oldItem.id == newItem.id
}