package echomachine.com.flickrapi_v1.ui.map;
import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import echomachine.com.flickrapi_v1.Constant;
import echomachine.com.flickrapi_v1.utilties.HelperMethods;

public class MapViewModel extends ViewModel {
    private static final String TAG = "ZiadLoc";

}