package alfianyusufabdullah.corona.ui

import alfianyusufabdullah.corona.R
import alfianyusufabdullah.corona.base.BaseMapsActivity
import alfianyusufabdullah.corona.data.entity.DataResponse
import alfianyusufabdullah.corona.data.entity.Location
import android.transition.TransitionManager
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity : BaseMapsActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override val contentId = R.layout.activity_maps
    override val mapsId = R.id.maps_corona

    override fun onMarkerClick(marker: Marker) {
        TransitionManager.beginDelayedTransition(rootParent)

        cardLocation.visibility = View.VISIBLE
        textLocation.text = marker.title

        val snippets = marker.snippet.split("::").toTypedArray()

        renderDashboardValue(*snippets)
    }

    override fun onMapClick() {
        TransitionManager.beginDelayedTransition(rootParent)

        cardLocation.visibility = View.GONE
        mainViewModel.reloadInformationOnDashboard()
    }

    override fun onMapReady() {
        mainViewModel.retrieveCoronaInfectedLocationData()
    }

    override fun onCreate() {
        mainViewModel.location.observe(this, Observer {
            renderInfectedLocationMarker(it)
        })

        mainViewModel.data.observe(this, Observer {
            renderDashboard(it)
        })

        mainViewModel.loading.observe(this, Observer {
            loadingState(it)
        })

        fabRefresh.setOnClickListener {
            mMap?.let {
                mainViewModel.retrieveCoronaInfectedLocationData()

                renderDashboardValue("-", "-", "-", "-")
                cardLocation.visibility = View.GONE

                moveCameraToCenter()
                mMap?.clear()
            }
        }
    }

    private fun loadingState(isLoading: Boolean) {
        TransitionManager.beginDelayedTransition(rootParent)
        if (isLoading) {
            fabRefresh.visibility = View.INVISIBLE
            cardLoading.visibility = View.VISIBLE

            mMap?.clear()
        } else {
            fabRefresh.visibility = View.VISIBLE
            cardLoading.visibility = View.GONE
        }
    }

    private fun renderDashboard(data: DataResponse) {
        TransitionManager.beginDelayedTransition(infoParent)

        renderDashboardValue(
            data.confirmed?.value.toString(),
            data.recovered?.value.toString(),
            data.deaths?.value.toString(),
            "${data.lastUpdate}"
        )
    }

    private fun renderDashboardValue(vararg value: String) {
        textConfirmed.text = value[CONFIRMED_INDEX]
        textRecovered.text = value[RECOVERED_INDEX]
        textDeath.text = value[DEATH_INDEX]

        textLastUpdate.text = "last update ${value[LAST_UPDATE_INDEX]}"
    }

    private fun renderInfectedLocationMarker(location: Location) {
        val latLng = LatLng(location.latitude ?: 0.0, location.longitude ?: 0.0)

        val snippet =
            "${location.confirmed}::${location.recovered}::${location.deaths}::${location.readableLastUpdate}"

        mMap?.addMarker(
            MarkerOptions()
                .title(location.countryRegion)
                .position(latLng)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_with_border))
        )
    }
}