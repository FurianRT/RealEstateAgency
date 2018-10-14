package com.furianrt.realestateagency.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.furianrt.realestateagency.data.model.Apartment
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ApartmentDao {

    @Insert
    fun insert(apartment: Apartment): Long

    @Insert
    fun insertAll(apartments: List<Apartment>): List<Long>

    @Delete
    fun delete(apartment: Apartment)

    @Delete
    fun delete(apartments: List<Apartment>)

    @Query("SELECT * FROM Apartments WHERE id = :apartmentId")
    fun getApartment(apartmentId: Long): Single<Apartment>

    @Query("SELECT * FROM Apartments WHERE id_house = :houseId")
    fun getApartments(houseId: Long): Flowable<List<Apartment>>
}