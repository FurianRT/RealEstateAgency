package com.furianrt.realestateagency.apartmentlist.adapterlist

import androidx.recyclerview.widget.DiffUtil
import com.furianrt.realestateagency.data.model.Apartment

class ApartmentDiffCallback(
        private val mOldList: List<Apartment>,
        private val mNewList: List<Apartment>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            mOldList[oldItemPosition].id == mNewList[newItemPosition].id &&
                    mOldList[oldItemPosition].houseId == mNewList[newItemPosition].houseId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            mOldList[oldItemPosition] == mNewList[newItemPosition]

    override fun getOldListSize() = mOldList.size

    override fun getNewListSize() = mNewList.size
}