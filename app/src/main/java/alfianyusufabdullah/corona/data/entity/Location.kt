package alfianyusufabdullah.corona.data.entity

import com.google.gson.annotations.SerializedName

data class Location(

	@field:SerializedName("recovered")
	val recovered: Int? = null,

	@field:SerializedName("countryRegion")
	var countryRegion: String? = null,

	@field:SerializedName("lastUpdate")
	val lastUpdate: Long? = null,

	var readableLastUpdate: String? = null,

	@field:SerializedName("confirmed")
	val confirmed: Int? = null,

	@field:SerializedName("provinceState")
	val provinceState: String? = null,

	@field:SerializedName("lat")
	val latitude: Double? = null,

	@field:SerializedName("long")
	val longitude: Double? = null,

	@field:SerializedName("deaths")
	val deaths: Int? = null
)