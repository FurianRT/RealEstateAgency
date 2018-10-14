package com.furianrt.realestateagency.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(
        tableName = "Apartments",
        indices = [Index("id_house")],
        primaryKeys = ["id", "id_house"],
        foreignKeys = [
            ForeignKey(
                    entity = House::class,
                    parentColumns = ["id"],
                    childColumns = ["id_house"],
                    onDelete = ForeignKey.CASCADE
            )
        ]
)
data class Apartment(
        @SerializedName("id") @ColumnInfo(name = "id") val id: Long,
        @SerializedName("id_house") @ColumnInfo(name = "id_house") val houseId: Long,
        @SerializedName("floor") @ColumnInfo(name = "floor") val floor: Int,
        @SerializedName("square") @ColumnInfo(name = "square") val square: Int
)