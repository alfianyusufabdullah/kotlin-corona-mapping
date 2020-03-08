package alfianyusufabdullah.corona.util

import alfianyusufabdullah.corona.data.entity.DataResponse
import alfianyusufabdullah.corona.data.entity.Location
import java.text.SimpleDateFormat
import java.util.*

class Mapper {

    fun locationNameMapper(data: Array<Location>?) = data?.map {
        val name = it.provinceState ?: it.countryRegion
        it.copy().apply {
            countryRegion = name
        }
    }

    fun locationLastUpdateMapper(data: Array<Location>?) = data?.map {
        val newFormat = SimpleDateFormat("HH:mm - dd MMM, yyyy", Locale.getDefault())
        val newDate = Date(it.lastUpdate ?: 0L)

        it.copy().apply {
            readableLastUpdate = newFormat.format(newDate)
        }
    }

    fun lastUpdateMapper(dataResponse: DataResponse): DataResponse {
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = utcFormat.parse(dataResponse.lastUpdate ?: "2020-03-07T15:03:06.000Z") as Date
        val newFormat = SimpleDateFormat("HH:mm - dd MMM, yyyy", Locale.getDefault())

        return dataResponse.copy().apply {
            lastUpdate = newFormat.format(date)
        }
    }
}