package net.jahez.jahezchallenge.Model

import com.google.gson.annotations.SerializedName

data class RestaurantModel (

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("hours")
	val hours: String? = null,

	@field:SerializedName("distance")
	val distance: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("hasOffer")
	val hasOffer: Boolean? = null
) : Comparable<Int>{
	override fun compareTo(other: Int): Int {
		TODO("Not yet implemented")
	}
}
