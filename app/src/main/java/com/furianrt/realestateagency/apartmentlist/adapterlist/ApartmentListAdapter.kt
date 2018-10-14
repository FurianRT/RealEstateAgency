package com.furianrt.realestateagency.apartmentlist.adapterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.furianrt.realestateagency.R
import com.furianrt.realestateagency.data.model.Apartment
import kotlinx.android.synthetic.main.activity_apartment_list_item.view.*

class ApartmentListAdapter(
        var listener: OnApartmentListInteractionListener? = null
) : RecyclerView.Adapter<ApartmentListAdapter.ApartmentListViewHolder>() {

    var list: MutableList<Apartment> = mutableListOf()
        set(value) {
            val diffCallback = ApartmentDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApartmentListViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_apartment_list_item, parent, false)
        return ApartmentListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ApartmentListViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount() = list.size

    inner class ApartmentListViewHolder(
            private val mView: View
    ) : RecyclerView.ViewHolder(mView), View.OnClickListener {

        private lateinit var mApartment: Apartment

        fun bindData(apartment: Apartment) {
            mApartment = apartment
            mView.apply {
                setOnClickListener(this@ApartmentListViewHolder)
                text_apartment_id.text = mApartment.id.toString()
            }
        }

        override fun onClick(v: View?) {
            when (v?.id) {
                R.id.card_apartment_list -> listener?.onListItemClick(mApartment)
            }
        }
    }

    interface OnApartmentListInteractionListener {

        fun onListItemClick(apartment: Apartment)
    }
}