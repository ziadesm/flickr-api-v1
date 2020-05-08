package android.com.flickrapi_v1.receiver.network;
import android.com.flickrapi_v1.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ConnectivityReceiver extends BroadcastReceiver {

    private ConnectivityReceiverListener mConnectivityReceiverListener;

    ConnectivityReceiver(ConnectivityReceiverListener listener) {
        mConnectivityReceiverListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mConnectivityReceiverListener.onNetworkConnectionChanged(isConnected(context));
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
//public class NetworkChangeReceiver extends BroadcastReceiver {

//    private static final String TAG = "NetworkChangeReceiver";
//    AlertDialog showAndhide;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        if (isOnline(context)) {
//            showAndhide = null;
//        } else {
//            showAndhide = null;
//            showAndhide = new MaterialAlertDialogBuilder(context, R.style.ThemeOverlay_App_MaterialAlertDialog)
//                    .setTitle(R.string.network_not_available)
//                    .setMessage(R.string.network_not_available_message)
//                    .setOnDismissListener(dialog -> showAndhide.dismiss())
//                    .show();
//        }
//    }
//
//    private boolean isOnline(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = null;
//        if (cm != null) {
//            activeNetworkInfo = cm.getActiveNetworkInfo();
//        }
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//}