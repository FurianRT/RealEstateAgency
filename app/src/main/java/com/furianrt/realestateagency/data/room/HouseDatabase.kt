package com.furianrt.realestateagency.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.furianrt.realestateagency.data.model.Apartment
import com.furianrt.realestateagency.data.model.House

@Database(
        entities = [House::class, Apartment::class],
        version = 1,
        exportSchema = false
)
abstract class HouseDatabase : RoomDatabase() {

    abstract fun houseDao(): HouseDao

    abstract fun apartmentDao(): ApartmentDao
}