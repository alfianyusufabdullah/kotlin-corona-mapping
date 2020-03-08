package alfianyusufabdullah.corona.data.entity

import com.google.gson.annotations.SerializedName

data class DataResponse(

	@field:SerializedName("recovered")
	val recovered: Data? = null,

	@field:SerializedName("lastUpdate")
	var lastUpdate: String? = null,

	@field:SerializedName("confirmed")
	val confirmed: Data? = null,

	@field:SerializedName("deaths")
	val deaths: Data? = null
)