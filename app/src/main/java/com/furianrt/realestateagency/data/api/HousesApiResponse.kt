package com.furianrt.realestateagency.data.api

import com.furianrt.realestateagency.data.model.Apartment
import com.google.gson.annotations.SerializedName

data class HousesApiResponse(
        @SerializedName("id")  val id: Long,
        @SerializedName("name") val name: String,
        @SerializedName("floors") val floors: Int,
        @SerializedName("company") val company: String,
        @SerializedName("apartments") val apartments: List<Apartment>
)