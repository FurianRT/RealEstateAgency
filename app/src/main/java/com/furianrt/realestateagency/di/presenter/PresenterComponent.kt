package com.furianrt.realestateagency.di.presenter

import com.furianrt.realestateagency.apartment.ApartmentActivity
import com.furianrt.realestateagency.apartmentlist.ApartmentListActivity
import com.furianrt.realestateagency.house.HouseActivity
import com.furianrt.realestateagency.houselist.HouseListActivity
import dagger.Subcomponent

@PresenterScope
@Subcomponent(modules = [PresenterModule::class])
interface PresenterComponent {

    fun inject(activity: HouseListActivity)

    fun inject(activity: HouseActivity)

    fun inject(activity: ApartmentListActivity)

    fun inject(activity: ApartmentActivity)
}