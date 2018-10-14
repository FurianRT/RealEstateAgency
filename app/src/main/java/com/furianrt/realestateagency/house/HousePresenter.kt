package com.furianrt.realestateagency.house

import com.furianrt.realestateagency.data.DataManager
import com.furianrt.realestateagency.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class HousePresenter(
        private val mDataManager: DataManager,
        private val mSchedulerProvider: BaseSchedulerProvider
) : HouseContract.Presenter {

    private var mView: HouseContract.View? = null
    private val mCompositeDisposable = CompositeDisposable()

    override fun loadHouse(houseId: Long) {
        val disposable = mDataManager.getHouse(houseId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe { house -> mView?.showHouseInfo(house) }

        mCompositeDisposable.add(disposable)
    }

    override fun attachView(view: HouseContract.View) {
        mView = view
    }

    override fun detachView() {
        mCompositeDisposable.clear()
        mView = null
    }
}