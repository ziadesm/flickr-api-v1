package echomachine.com.flickrapi_v1.utilties;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

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

    /**
     // method for turn on GPS
     public static void turnGPSOn(Context context) {
     LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
     SettingsClient mSettingsClient = LocationServices.getSettingsClient(context);
     LocationRequest locationRequest = LocationRequest.create();
     locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
     locationRequest.setInterval(10 * 1000);
     locationRequest.setFastestInterval(2 * 1000);
     LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
     .addLocationRequest(locationRequest);
     LocationSettingsRequest mLocationSettingsRequest = builder.build();
     //**************************
     builder.setAlwaysShow(true);

     if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
     mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
     .addOnFailureListener((Activity) context, (OnFailureListener) e -> {
     int statusCode = ((ApiException) e).getStatusCode();
     switch (statusCode) {
     case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
     try {
     // Show the dialog by calling startResolutionForResult(), and check the
     // result in onActivityResult().
     ResolvableApiException rae = (ResolvableApiException) e;
     rae.startResolutionForResult((Activity) context, Constant.GPS_DIALOG_REQUEST);
     } catch (IntentSender.SendIntentException sie) {
     Log.i(TAG, "PendingIntent unable to execute request.");
     }
     break;
     case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
     String errorMessage = "Location settings are inadequate, and cannot be " +
     "fixed here. Fix in Settings.";
     Log.e(TAG, errorMessage);
     Toast.makeText((Activity) context, errorMessage, Toast.LENGTH_LONG).show();
     }
     });
     }
     }

     **/
    /**
    public static boolean checkGPSEnabled(Context context) {
        return GPSLocationReceiver.isGPSEnabled(context);
    }

     **/
}
