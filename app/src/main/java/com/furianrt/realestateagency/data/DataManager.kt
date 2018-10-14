package com.furianrt.realestateagency.data

import com.furianrt.realestateagency.data.model.Apartment
import com.furianrt.realestateagency.data.model.House
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface DataManager {

    fun insertHouse(house: House): Single<Long>

    fun insertApartment(apartment: Apartment): Single<Long>

    fun deleteAllHouses(): Completable

    fun deleteApartment(apartment: Apartment): Completable

    fun deleteApartments(apartments: List<Apartment>): Completable

    fun getHouse(houseId: Long): Single<House>

    fun getHouses(): Flowable<List<House>>

    fun getApartment(apartmentId: Long): Single<Apartment>

    fun getApartments(houseId: Long): Flowable<List<Apartment>>

    fun syncHousesWithServer(): Completable
}