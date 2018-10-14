package com.furianrt.realestateagency.houselist

import com.furianrt.realestateagency.data.DataManager
import com.furianrt.realestateagency.data.model.House
import com.furianrt.realestateagency.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class HouseListPresenter(
        private val mDataManager: DataManager,
        private val mSchedulerProvider: BaseSchedulerProvider
) : HouseListContract.Presenter {

    private var mView: HouseListContract.View? = null
    private val mCompositeDisposable = CompositeDisposable()

    override fun attachView(view: HouseListContract.View) {
        mView = view
    }

    override fun detachView() {
        mCompositeDisposable.clear()
        mView = null
    }

    override fun loadHouses() {
        val disposable = mDataManager.getHouses()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe { houses ->
                    if (houses.isEmpty()) {
                        onButtonSyncClick()
                    } else {
                        mView?.showHouses(houses)
                    }
                }

        mCompositeDisposable.add(disposable)
    }

    override fun onButtonSyncClick() {
        mView?.apply {
            if (isNetworkAvailable()) {
                showLoadingIndicator()
                val disposable = mDataManager.syncHousesWithServer()
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe { hideLoadingIndicator() }
                mCompositeDisposable.add(disposable)
            } else {
                showNetworkErrorMessage()
            }
        }
    }

    override fun onHouseClick(house: House) {
        mView?.showViewHouse(house.id)
    }

    override fun onButtonApartmentListClick(house: House) {
        mView?.showViewApartmentList(house.id)
    }
}