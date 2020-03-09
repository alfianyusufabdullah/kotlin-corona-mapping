package alfianyusufabdullah.corona.base

import alfianyusufabdullah.corona.R
import android.os.Bundle
import android.transition.TransitionManager
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_maps.*

abstract class BaseMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val CONFIRMED_INDEX = 0
        const val RECOVERED_INDEX = 1
        const val DEATH_INDEX = 2
        const val LAST_UPDATE_INDEX = 3
    }

    var mMap: GoogleMap? = null
    abstract val contentId: Int
    abstract val mapsId: Int

    abstract fun onMarkerClick(marker: Marker)
    abstract fun onMapClick()
    abstract fun onMapReady()

    abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)

        setContentView(contentId)
        val mapFragment = supportFragmentManager
            .findFragmentById(mapsId) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onCreate()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.uiSettings?.isMapToolbarEnabled = false

        mMap?.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this,
                R.raw.map_style
            )
        )
        mMap?.setOnMarkerClickListener {
            onMarkerClick(it)

            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, 5f))
            true
        }

        mMap?.setOnMapClickListener {
            onMapClick()
        }

        onMapReady()
        moveCameraToCenter()
    }

    fun moveCameraToCenter(){
        val latLng = LatLng(-0.7893, 113.9213)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 3f))
    }
}
