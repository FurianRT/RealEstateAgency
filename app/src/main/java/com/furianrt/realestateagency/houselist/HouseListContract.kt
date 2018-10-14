package com.furianrt.realestateagency.houselist

import com.furianrt.realestateagency.BasePresenter
import com.furianrt.realestateagency.BaseView
import com.furianrt.realestateagency.data.model.House

interface HouseListContract {

    interface View : BaseView {

        fun showHouses(houses: List<House>)

        fun isNetworkAvailable(): Boolean

        fun showNetworkErrorMessage()

        fun showLoadingIndicator()

        fun hideLoadingIndicator()

        fun showViewHouse(houseId: Long)

        fun showViewApartmentList(houseId: Long)
    }

    interface Presenter : BasePresenter<View> {

        fun loadHouses()

        fun onButtonSyncClick()

        fun onHouseClick(house: House)

        fun onButtonApartmentListClick(house: House)
    }
}