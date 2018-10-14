package com.furianrt.realestateagency.houselist.adapterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.furianrt.realestateagency.R
import com.furianrt.realestateagency.data.model.House
import kotlinx.android.synthetic.main.activity_house_list_item.view.*

class HouseListAdapter(
        var listener: OnHouseListInteractionListener?
) : ListAdapter<House, HouseListAdapter.HouseListViewHolder>(HouseListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HouseListViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_house_list_item, parent, false)
        return HouseListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HouseListViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    inner class HouseListViewHolder(
            private val mView: View
    ) : RecyclerView.ViewHolder(mView), View.OnClickListener {

        private lateinit var mHouse: House

        fun bindData(house: House) {
            mHouse = house
            mView.apply {
                button_apartments_list.setOnClickListener(this@HouseListViewHolder)
                setOnClickListener(this@HouseListViewHolder)
                text_house_name_list.text = mHouse.name
            }
        }

        override fun onClick(v: View) {
            when (v.id) {
                R.id.card_house_list -> listener?.onListItemClick(mHouse)
                R.id.button_apartments_list -> listener?.onButtonApartmentListClick(mHouse)
            }
        }
    }

    interface OnHouseListInteractionListener {

        fun onListItemClick(house: House)

        fun onButtonApartmentListClick(house: House)
    }
}