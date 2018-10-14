package com.furianrt.realestateagency.house

import com.furianrt.realestateagency.BasePresenter
import com.furianrt.realestateagency.BaseView
import com.furianrt.realestateagency.data.model.House

interface HouseContract {

    interface View : BaseView {

        fun showHouseInfo(house: House)
    }

    interface Presenter : BasePresenter<View> {

        fun loadHouse(houseId: Long)
    }
}