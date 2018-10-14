package com.furianrt.realestateagency

import android.content.Context
import com.furianrt.realestateagency.di.presenter.PresenterComponent
import com.furianrt.realestateagency.di.presenter.PresenterModule

interface BaseView {

    fun getPresenterComponent(context: Context): PresenterComponent {
        return (context.applicationContext as MyApp)
                .component
                .newPresenterComponent(PresenterModule(context))
    }
}