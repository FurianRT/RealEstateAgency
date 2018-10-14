package com.furianrt.realestateagency.apartment

import com.furianrt.realestateagency.BasePresenter
import com.furianrt.realestateagency.BaseView
import com.furianrt.realestateagency.data.model.Apartment

interface ApartmentContract {

    interface View : BaseView {

        fun showApartmentInfo(apartment: Apartment)
    }

    interface Presenter : BasePresenter<View> {

        fun loadApartment(apartmentId: Long)
    }
}