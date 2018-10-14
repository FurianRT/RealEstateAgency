package com.furianrt.realestateagency.apartmentlist

import com.furianrt.realestateagency.data.DataManager
import com.furianrt.realestateagency.data.model.Apartment
import com.furianrt.realestateagency.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ApartmentListPresenter(
        private val mDataManager: DataManager,
        private val mSchedulerProvider: BaseSchedulerProvider
): ApartmentListContract.Presenter {

    private var mView: ApartmentListContract.View? = null
    private val mCompositeDisposable = CompositeDisposable()

    override fun attachView(view: ApartmentListContract.View) {
        mView = view
    }

    override fun detachView() {
        mCompositeDisposable.clear()
        mView = null
    }

    override fun loadApartments(houseId: Long) {
        val disposable = mDataManager.getApartments(houseId)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe { apartmentList -> mView?.showApartments(apartmentList) }

        mCompositeDisposable.add(disposable)
    }

    override fun onApartmentClick(apartment: Apartment) {
        mView?.showViewApartment(apartment.id)
    }

    override fun onApartmentsRemoved(apartments: List<Apartment>) {
        val disposable = mDataManager.deleteApartments(apartments)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe()

        mCompositeDisposable.add(disposable)
    }
}