package alfianyusufabdullah.corona.data.repository

import alfianyusufabdullah.corona.data.entity.DataResponse
import alfianyusufabdullah.corona.data.entity.Location
import alfianyusufabdullah.corona.data.source.DataSource
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONException

@ExperimentalCoroutinesApi
class DataRepository(private val dataSource: DataSource) {

    fun loadMainData() = flow {
        try {
            val response =
                Gson().fromJson<DataResponse>(dataSource.loadMainData(), DataResponse::class.java)

            emit(response)
        } catch (e: JSONException) {
            emit(null)
        }
    }.flowOn(Dispatchers.IO)

    fun loadDataWithLocation() = flow {
        try {
            val response: Array<Location> = Gson().fromJson(
                dataSource.loadDataWithLocation(),
                Array<Location>::class.java
            )

            emit(response)
        } catch (e: JSONException) {
            emit(null)
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}