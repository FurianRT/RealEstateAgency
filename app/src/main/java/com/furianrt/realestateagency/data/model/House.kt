package com.furianrt.realestateagency.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Houses")
data class House(
        @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "floors") val floors: Int,
        @ColumnInfo(name = "company") val company: String
)