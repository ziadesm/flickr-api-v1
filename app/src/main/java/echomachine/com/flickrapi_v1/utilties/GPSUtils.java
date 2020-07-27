package echomachine.com.flickrapi_v1.utilties;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;

import echomachine.com.flickrapi_v1.Constant;

public class GPSUtils {
    private static final String TAG = "ZiadLoc";

    public static void turnGPSOn(Context context) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener((Activity)context, locationSettingsResponse -> {
//            Log.d(TAG, "turnGPSOn: GPS is on already");
        });

        task.addOnFailureListener((Activity)context, e -> {
            if (e instanceof ResolvableApiException) {
                try {
                    ResolvableApiException res = (ResolvableApiException) e;
                    Log.d(TAG, "Dialog is on");
                    res.startResolutionForResult((Activity)context, Constant.GPS_DIALOG_REQUEST);
                } catch (IntentSender.SendIntentException ex) {
                    Log.d(TAG, "turnGPSOn: Failed" + ex.getMessage());
                }
            }
        });
    }

    public void geoLocateThis(String searchString, Context context) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(searchString, 1);
            if (addressList.size() > 0) {
//                moveCamera(new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude())
//                        , Constant.MAP_CAMERA_ZOOM, addressList.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
