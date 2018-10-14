package com.furianrt.realestateagency

import android.app.Application
import com.furianrt.realestateagency.di.application.AppComponent
import com.furianrt.realestateagency.di.application.AppModule
import com.furianrt.realestateagency.di.application.DaggerAppComponent

class MyApp : Application() {

    val component: AppComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
}