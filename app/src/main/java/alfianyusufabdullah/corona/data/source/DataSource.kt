package alfianyusufabdullah.corona.data.source

import java.net.URL

class DataSource {

    fun loadMainData()
            = URL("https://covid19.mathdro.id/api").readText()

    fun loadDataWithLocation()
            = URL("https://covid19.mathdro.id/api/confirmed").readText()
}