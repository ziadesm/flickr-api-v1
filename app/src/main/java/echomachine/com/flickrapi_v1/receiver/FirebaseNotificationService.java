package echomachine.com.flickrapi_v1.receiver;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import echomachine.com.flickrapi_v1.R;

public class FirebaseNotificationService extends FirebaseMessagingService {
    private static final String TAG = "ZiadNotify";
    private static final String FIREBASE_TOKEN = "firebase_token";
    private static final String CHANNEL_ID_1 = "Photo Channel";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification builder;
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String content = remoteMessage.getNotification().getBody();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID_1
                        , "Photo of the day"
                        , NotificationManager.IMPORTANCE_HIGH);
                channel1.setDescription("High Priority Notification");
                channel1.enableLights(true);
                channel1.enableVibration(true);

                manager.createNotificationChannel(channel1);
            }

            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_1)
                    .setSmallIcon(R.drawable.ic_place_holder_home)
                    .setWhen(System.currentTimeMillis())
                    .setContentText(content)
                    .setContentTitle(title)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setTicker("WWGallery")
                    .setAutoCancel(true)
                    .build();
            manager.notify(101, builder);
        }
    }

//    @Override
//    public void onNewToken(String s) {
////        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
////        Log.d(TAG, "Refreshed token: " + refreshedToken);
////        // If you want to send messages to this application instance or
////        // manage this apps subscriptions on the server side, send the
////        // Instance ID token to your app server.
////        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
////        preferences.edit().putString(FIREBASE_TOKEN,refreshedToken).apply();
//    }
}