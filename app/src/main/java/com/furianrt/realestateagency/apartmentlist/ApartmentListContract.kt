package com.furianrt.realestateagency.apartmentlist

import com.furianrt.realestateagency.BasePresenter
import com.furianrt.realestateagency.BaseView
import com.furianrt.realestateagency.data.model.Apartment

interface ApartmentListContract {

    interface View : BaseView {

        fun showApartments(apartmentList: List<Apartment>)

        fun showViewApartment(apartmentId: Long)
    }

    interface Presenter : BasePresenter<View> {

        fun loadApartments(houseId: Long)

        fun onApartmentClick(apartment: Apartment)

        fun onApartmentsRemoved(apartments: List<Apartment>)
    }
}