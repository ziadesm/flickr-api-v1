package echomachine.com.flickrapi_v1.ui.selected;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import echomachine.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.adapter.SelectedPhotoAdapter;
import echomachine.com.flickrapi_v1.utilties.HelperMethods;

import static echomachine.com.flickrapi_v1.Constant.ERROR_DIALOG_REQUEST;

public class SelectedFragment extends Fragment {
    private static final String TAG = "ZiadSelected";
    private SelectedViewModel viewModel;
    private NavController navController;
    private FloatingActionButton mChooseFab, mDownloadFab, mShareFab, mWallpaperFab, mMapFab;
    private Animation aOpenFabs, aCloseFabs, aOpenRotate, aCloseRotate;
    private ImageView mImageViewer;
    private RecyclerView mRecycler;
    private MotionLayout motion;
    private boolean isOpen;
    private SelectedPhotoAdapter adapter;

    public SelectedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Use to share images with other apps without restriction >= API 24
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        OnBackPressedCallback callback = new OnBackPressedCallback(true/* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected, container, false);

        // initialised views here
        motion = view.findViewById(R.id.selected_motion_scene);
        mChooseFab = view.findViewById(R.id.selected_floating_action_btn);
        mDownloadFab = view.findViewById(R.id.selected_download_floating_action_btn);
        mShareFab = view.findViewById(R.id.selected_share_floating_action_btn);
        mMapFab = view.findViewById(R.id.selected_map_floating_action_btn);
        mWallpaperFab = view.findViewById(R.id.selected_set_as_wallpaper_floating_action_btn);
        mImageViewer = view.findViewById(R.id.selected_image_header);
        mRecycler = view.findViewById(R.id.selected_recycler_view);


        viewModel = ViewModelProviders
                .of(this).get(SelectedViewModel.class);

        // Animation initialised here
        aOpenFabs = AnimationUtils.loadAnimation(getContext(), R.anim.anim_fab_opens);
        aCloseFabs = AnimationUtils.loadAnimation(getContext(), R.anim.anim_fab_closes);
        aOpenRotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_open_main_fab);
        aCloseRotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_close_main_fab);

        motion.onStartTemporaryDetach();

        // Floating action button functionality
        mChooseFab.setOnClickListener(v -> {
              if (isOpen) {
                  closeFabToCloseFabs();
                  isOpen = false;
              } else {
                  openFabToOpenFabs();
                  isOpen = true;
              }
        });
        mShareFab.setOnClickListener(v -> {
            if (getArguments() != null) {
                SelectedFragmentArgs args = SelectedFragmentArgs.fromBundle(getArguments());
                HelperMethods.shareItem(args.getPhotoUrl(), getActivity(), getContext());
            }
        });
        mDownloadFab.setOnClickListener(v -> {
            if (getArguments() != null && HelperMethods
                    .isStoragePermissionGranted(getContext(), getActivity())) {
                SelectedFragmentArgs args = SelectedFragmentArgs.fromBundle(getArguments());
                HelperMethods.downloadPhoto(args.getPhotoUrl(), getContext());
            } else {
                Toast.makeText(getContext(), "Please allow us to your storage", Toast.LENGTH_LONG).show();
            }
        });
        mWallpaperFab.setOnClickListener(v -> {
            WallpaperManager manager = WallpaperManager.getInstance(getContext());

            try {
                File file =  new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        , "ww_gallery" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                Bitmap bitmap = ((BitmapDrawable)mImageViewer.getDrawable())
                        .getBitmap();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                manager.setBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        mMapFab.setOnClickListener(v -> { goToMapFragment(); });

        Log.d(TAG, "I'M HERE BY THE WAY");
        adapter = new SelectedPhotoAdapter();
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        viewModel.getMutableLiveData().observe(getActivity(), photos -> adapter.setList(photos));
        mRecycler.setAdapter(adapter);

        return view;
    }

    private void goToMapFragment() {
        int available = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(getContext());

        if (available == ConnectionResult.SUCCESS) {
            navController.navigate(R.id.navigation_map);
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance()
                    .getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.setCancelable(true);
            dialog.show();
            Toast.makeText(getContext(), "Play service has error but we can fix it", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Play service is not available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view
            , @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Getting passing data between HomeFragment & LikedFragment --> SelectedFragment
        if (getArguments() != null) {
            SelectedFragmentArgs args = SelectedFragmentArgs.fromBundle(getArguments());
            viewModel.setUserId(args.getPhotoOwner());
            viewModel.setImageUri(args.getPhotoUrl());
        }

        if (viewModel.getImageUri() != null) {
            Picasso.get()
                    .load(viewModel.getImageUri())
                    .placeholder(R.drawable.ic_place_holder_home)
                    .fit()
                    .into(mImageViewer);
        }

        if (viewModel.getUserId() != null) {
            viewModel.getProfilePhotosPage(viewModel.getUserId());
        }
    }

    private void openFabToOpenFabs() {
        mChooseFab.startAnimation(aOpenRotate);

        mShareFab.startAnimation(aOpenFabs);
        mWallpaperFab.startAnimation(aOpenFabs);
        mDownloadFab.startAnimation(aOpenFabs);
    }

    private void closeFabToCloseFabs() {
        mChooseFab.startAnimation(aCloseRotate);

        mShareFab.startAnimation(aCloseFabs);
        mWallpaperFab.startAnimation(aCloseFabs);
        mDownloadFab.startAnimation(aCloseFabs);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            , @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SelectedFragmentArgs args = SelectedFragmentArgs.fromBundle(getArguments());
            HelperMethods.downloadPhoto(args.getPhotoUrl(), getContext());
        }
    }
}