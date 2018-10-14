package com.furianrt.realestateagency.data

import com.furianrt.realestateagency.data.api.HousesApiResponse
import com.furianrt.realestateagency.data.api.HousesApiService
import com.furianrt.realestateagency.data.model.Apartment
import com.furianrt.realestateagency.data.model.House
import com.furianrt.realestateagency.data.room.HouseDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

private fun HousesApiResponse.toHouse(): House = House(id, name, floors, company)

class DataManagerImp(
        private val mDatabase: HouseDatabase,
        private val mHousesApi: HousesApiService
) : DataManager {

    override fun insertHouse(house: House): Single<Long> =
            Single.fromCallable { mDatabase.houseDao().insert(house) }

    override fun insertApartment(apartment: Apartment): Single<Long> =
            Single.fromCallable { mDatabase.apartmentDao().insert(apartment) }

    override fun deleteAllHouses(): Completable =
            Completable.fromAction { mDatabase.houseDao().deleteAll() }

    override fun deleteApartment(apartment: Apartment): Completable =
            Completable.fromAction { mDatabase.apartmentDao().delete(apartment) }

    override fun deleteApartments(apartments: List<Apartment>): Completable =
            Completable.fromAction { mDatabase.apartmentDao().delete(apartments) }

    override fun getHouse(houseId: Long): Single<House> =
            mDatabase.houseDao()
                    .getHouse(houseId)

    override fun getHouses(): Flowable<List<House>> =
            mDatabase.houseDao()
                    .getAll()

    override fun getApartment(apartmentId: Long): Single<Apartment> =
            mDatabase.apartmentDao()
                    .getApartment(apartmentId)

    override fun getApartments(houseId: Long): Flowable<List<Apartment>> =
            mDatabase.apartmentDao()
                    .getApartments(houseId)

    override fun syncHousesWithServer(): Completable =
            mHousesApi.getHouses()
                    .doOnSuccess { responseList ->
                        mDatabase.runInTransaction {
                            mDatabase.houseDao().deleteAll()
                            mDatabase.houseDao().insertAll(responseList.map { it.toHouse() })
                            responseList.forEach {
                                mDatabase.apartmentDao().insertAll(it.apartments)
                            }
                        }
                    }
                    .toCompletable()
}