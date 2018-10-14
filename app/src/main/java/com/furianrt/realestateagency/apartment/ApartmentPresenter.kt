package com.furianrt.realestateagency.apartment

import com.furianrt.realestateagency.data.DataManager
import com.furianrt.realestateagency.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ApartmentPresenter(
        private val mDataManager: DataManager,
        private val mSchedulerProvider: BaseSchedulerProvider
) : ApartmentContract.Presenter {

    private var mView: ApartmentContract.View? = null
    private val mCompositeDisposable = CompositeDisposable()

    override fun attachView(view: ApartmentContract.View) {
        mView = view
    }

    override fun detachView() {
        mCompositeDisposable.clear()
        mView = null
    }

    override fun loadApartment(apartmentId: Long) {
        val disposable = mDataManager.getApartment(apartmentId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe { apartment -> mView?.showApartmentInfo(apartment) }

        mCompositeDisposable.add(disposable)
    }
}