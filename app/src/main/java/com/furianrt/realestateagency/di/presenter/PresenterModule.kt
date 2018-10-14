package com.furianrt.realestateagency.di.presenter

import android.content.Context
import com.furianrt.realestateagency.apartment.ApartmentContract
import com.furianrt.realestateagency.apartment.ApartmentPresenter
import com.furianrt.realestateagency.apartmentlist.ApartmentListContract
import com.furianrt.realestateagency.apartmentlist.ApartmentListPresenter
import com.furianrt.realestateagency.data.DataManager
import com.furianrt.realestateagency.house.HouseContract
import com.furianrt.realestateagency.house.HousePresenter
import com.furianrt.realestateagency.houselist.HouseListPresenter
import com.furianrt.realestateagency.houselist.HouseListContract
import com.furianrt.realestateagency.utils.schedulers.BaseSchedulerProvider
import com.furianrt.realestateagency.utils.schedulers.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class PresenterModule(private val context: Context) {

    @Provides
    @PresenterScope
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider()

    @Provides
    @PresenterScope
    fun provideHouseListPresenter(dataManager: DataManager, scheduler: BaseSchedulerProvider)
            : HouseListContract.Presenter = HouseListPresenter(dataManager, scheduler)

    @Provides
    @PresenterScope
    fun provideHousePresenter(dataManager: DataManager, scheduler: BaseSchedulerProvider)
            : HouseContract.Presenter = HousePresenter(dataManager, scheduler)

    @Provides
    @PresenterScope
    fun provideApartmentListPresenter(dataManager: DataManager, scheduler: BaseSchedulerProvider)
            : ApartmentListContract.Presenter = ApartmentListPresenter(dataManager, scheduler)

    @Provides
    @PresenterScope
    fun provideApartmentPresenter(dataManager: DataManager, scheduler: BaseSchedulerProvider)
            : ApartmentContract.Presenter = ApartmentPresenter(dataManager, scheduler)
}