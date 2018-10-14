package com.furianrt.realestateagency.utils.schedulers

import io.reactivex.Scheduler

interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}