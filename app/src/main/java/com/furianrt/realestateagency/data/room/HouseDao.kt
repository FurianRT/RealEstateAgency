package com.furianrt.realestateagency.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.furianrt.realestateagency.data.model.House
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface HouseDao {

    @Insert
    fun insert(house: House): Long

    @Insert
    fun insertAll(houses: List<House>): List<Long>

    @Query("DELETE FROM houses")
    fun deleteAll()

    @Query("SELECT * FROM Houses WHERE id = :houseId")
    fun getHouse(houseId: Long): Single<House>

    @Query("SELECT * FROM Houses")
    fun getAll(): Flowable<List<House>>
}