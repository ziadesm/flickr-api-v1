package echomachine.com.flickrapi_v1.ui.selected;
import android.Manifest;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import echomachine.com.flickrapi_v1.R;

public class SelectedFragment extends Fragment {

    private static final String TAG = "ZiadSelected";
    private static final int STORAGE_PERMISSION_REQUEST = 101;
    private SelectedViewModel viewModel;
    private NavController navController;
    private FloatingActionButton mChooseFab, mDownloadFab, mShareFab, mWallpaperFab;
    private Animation aOpenFabs, aCloseFabs, aOpenRotate, aCloseRotate;
    private ImageView mImageViewer;
    private RecyclerView mRecycler;
    private boolean isOpen;


    public SelectedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mChooseFab = view.findViewById(R.id.selected_floating_action_btn);
        mDownloadFab = view.findViewById(R.id.selected_download_floating_action_btn);
        mShareFab = view.findViewById(R.id.selected_share_floating_action_btn);
        mWallpaperFab = view.findViewById(R.id.selected_set_as_wallpaper_floating_action_btn);
        mImageViewer = view.findViewById(R.id.selected_image_header);
        mRecycler = view.findViewById(R.id.selected_recycler_view);

        // Animation initialised here
        aOpenFabs = AnimationUtils.loadAnimation(getContext(), R.anim.anim_fab_opens);
        aCloseFabs = AnimationUtils.loadAnimation(getContext(), R.anim.anim_fab_closes);
        aOpenRotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_open_main_fab);
        aCloseRotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_close_main_fab);

        viewModel = ViewModelProviders
                .of(this).get(SelectedViewModel.class);

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
                shareItem(args.getPhotoUrl());
            }
        });

        mDownloadFab.setOnClickListener(v -> {
            if (getArguments() != null && isStoragePermissionGranted()) {
                SelectedFragmentArgs args = SelectedFragmentArgs.fromBundle(getArguments());
                downloadPhoto(args.getPhotoUrl());
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

        return view;
    }

    public void downloadPhoto(String url) {
        Uri Download_Uri = Uri.parse(url);
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Downloading");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading File");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS
                , "wwgallery" + "_" + System.currentTimeMillis() + ".jpg");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        downloadManager.enqueue(request);
    }

    @Override
    public void onViewCreated(@NonNull View view
            , @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            SelectedFragmentArgs args = SelectedFragmentArgs.fromBundle(getArguments());
            String url = args.getPhotoUrl();
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_place_holder_home)
                    .into(mImageViewer);
            Log.d(TAG, "" + url);
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

    public void shareItem(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    , "ww_gallery" + System.currentTimeMillis() + ".png");

            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode
            , @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SelectedFragmentArgs args = SelectedFragmentArgs.fromBundle(getArguments());
            downloadPhoto(args.getPhotoUrl());
        }
    }
}