package echomachine.com.flickrapi_v1.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import echomachine.com.flickrapi_v1.MyApp;
import echomachine.com.flickrapi_v1.R;

public class FirebaseNotificationService extends FirebaseMessagingService {
    private static final String TAG = "ZiadNotify";
    private static final String FIREBASE_TOKEN = "firebase_token";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData() != null) {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String content = data.get("content");

            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MyApp.getINSTANCE().createNotificationChannel();
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MyApp.CHANNEL_ID_1);
            builder.setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_icon_app_launcher_round)
                    .setTicker("WWGallery");

            manager.notify(1, builder.build());

        }
    }

    @Override
    public void onNewToken(String s) {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.d(TAG, refreshedToken);
        preferences.edit().putString(FIREBASE_TOKEN,refreshedToken).apply();
    }
}