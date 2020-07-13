package echomachine.com.flickrapi_v1.utilties;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import echomachine.com.flickrapi_v1.Constant;

public class HelperMethods {
    public static void downloadPhoto(String url, Context context) {
        Uri Download_Uri = Uri.parse(url);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
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

    public static void shareItem(String url, Activity activity, Context context) {
        Picasso.get().load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, context));
                activity.startActivity(Intent.createChooser(i, "Share Image"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

    public static Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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

    public static boolean isStoragePermissionGranted(Context context, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                activity.requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constant.STORAGE_PERMISSION_REQUEST);
                return false;
            }
        } else {
            return true;
        }
    }

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}