package com.furianrt.realestateagency.di.application

import com.furianrt.realestateagency.di.presenter.PresenterComponent
import com.furianrt.realestateagency.di.presenter.PresenterModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    fun newPresenterComponent(module: PresenterModule): PresenterComponent
}