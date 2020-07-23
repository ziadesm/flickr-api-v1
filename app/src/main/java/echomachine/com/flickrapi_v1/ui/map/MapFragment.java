package echomachine.com.flickrapi_v1.ui.map;
import androidx.activity.OnBackPressedCallback;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import echomachine.com.flickrapi_v1.Constant;
import echomachine.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.utilties.GPSUtils;
import echomachine.com.flickrapi_v1.utilties.HelperMethods;

import static android.app.Activity.RESULT_OK;
import static echomachine.com.flickrapi_v1.Constant.AUTOCOMPLETE_REQUEST_KEY;
import static echomachine.com.flickrapi_v1.Constant.GPS_DIALOG_REQUEST;
import static echomachine.com.flickrapi_v1.Constant.KEY_LOCATION_REQUEST;

public class MapFragment extends Fragment
        implements OnMapReadyCallback{
    private static final String TAG = "ZiadLoc";
    private static final String FINE_LOCATION_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private MapViewModel mViewModel;
    private GoogleMap mMap;
    private NavController navController;
    //Widget
    private EditText searchText;
    private ImageView mGpsImage;
    // vars
    private boolean isLocationPermissionGranted = false;
    private String[] permissions = {FINE_LOCATION_PERMISSION, COARSE_LOCATION_PERMISSION};

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (isLocationPermissionGranted) {
            getDeviceLocation();
            Log.d(TAG, "onMapReady: HERE");

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();
        }
    }

    private void isLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), FINE_LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getActivity(),COARSE_LOCATION_PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
                isLocationPermissionGranted = true;
                initGoogleMap();
            } else {
                ActivityCompat.requestPermissions(getActivity()
                , permissions,KEY_LOCATION_REQUEST);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity()
                    , permissions
                    , KEY_LOCATION_REQUEST);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Places.initialize(getActivity().getApplicationContext(), "AIzaSyDhZ1cMcZji9wWYwP4ZPT-H34AtjThVoLk");
        mViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        searchText = view.findViewById(R.id.map_fragment_et_search);
        mGpsImage = view.findViewById(R.id.map_fragment_ic_gps);

        return view;
    }

    private void initGoogleMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isLocationPermission();
    }

    private void init() {
        searchText.setOnClickListener(v -> {
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.OVERLAY,
                    Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME))
                    .build(getContext());
            startActivityForResult(intent, Constant.AUTOCOMPLETE_REQUEST_KEY);
        });

        mGpsImage.setOnClickListener(v -> {
            LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getDeviceLocation();
            } else{
                GPSUtils.turnGPSOn(getContext());
            }
        });
    }

    private void geoLocateThis(String searchString) {
        Geocoder geocoder = new Geocoder(requireContext());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(searchString, 1);
            if (addressList.size() > 0) {
                moveCamera(new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude())
                        , Constant.MAP_CAMERA_ZOOM, addressList.get(0).getAddressLine(0));
            }
        } catch (IOException e) {
            Toast.makeText(requireContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            , @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        isLocationPermissionGranted = false;
        switch (requestCode) {
            case KEY_LOCATION_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    isLocationPermissionGranted = true;
                    Log.d(TAG, "getDeviceLocation: " + "onRequestPermissionResult");
                    initGoogleMap();
                } else {
                    ActivityCompat.requestPermissions(requireActivity()
                            , permissions
                            , KEY_LOCATION_REQUEST);
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GPS_DIALOG_REQUEST:
                if (resultCode == RESULT_OK) {
                    getDeviceLocation();
                } else {
                    GPSUtils.turnGPSOn(getContext());
                }
                break;
            case AUTOCOMPLETE_REQUEST_KEY:
                if (resultCode == RESULT_OK) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    moveCamera(place.getLatLng(),Constant.MAP_CAMERA_ZOOM, place.getAddress());
                } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Log.d(TAG, "" + status.getStatusMessage());
                    Toast.makeText(getContext(), "ERROR: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "You have been cancelled the operation", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: " + latLng.latitude + "\n" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        mMap.addMarker(options);
    }

    public void getDeviceLocation() {
        FusedLocationProviderClient fused = LocationServices.getFusedLocationProviderClient(getActivity());
        try {
            if (isLocationPermissionGranted) {
                fused.getLastLocation().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Location location = task.getResult();
                        if (location != null) {
                            Log.d(TAG, "getDeviceLocation: " + "METHOD");
                            moveCamera(new LatLng(location.getLatitude(),
                                            location.getLongitude())
                                    , Constant.MAP_CAMERA_ZOOM, "My Location");
                        }
                    } else{
                        Log.d(TAG, "getDeviceLocation: We could not get your location");
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: Error message");
        }
    }
}