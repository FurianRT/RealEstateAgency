package com.furianrt.realestateagency

interface BasePresenter<in T : BaseView> {

    fun attachView(view: T)

    fun detachView()
}