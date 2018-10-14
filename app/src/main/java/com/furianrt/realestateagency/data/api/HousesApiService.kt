package com.furianrt.realestateagency.data.api

import io.reactivex.Single
import retrofit2.http.GET

interface HousesApiService {

    @GET("1ew5fk/")
    fun getHouses(): Single<List<HousesApiResponse>>
}