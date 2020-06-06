package echomachine.com.flickrapi_v1;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import echomachine.com.flickrapi_v1.receiver.network.ConnectivityReceiver;

public class MyApp extends Application {
    private static final String TAG = "ZiadNotify";
    private static MyApp INSTANCE;
    public static final String CHANNEL_ID_1 = "Photo Channel";
    public static final String CHANNEL_ID_2 = "User Channel";

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        createNotificationChannel();
    }

    public static synchronized MyApp getINSTANCE(){
        return INSTANCE;
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_ID_1
                    , "Photo of the day"
                    , NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is my first notification");
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setShowBadge(true);
            channel1.setLightColor(R.color.notify_color_high);

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID_2
                    , "Photo of the day"
                    , NotificationManager.IMPORTANCE_DEFAULT);
            channel2.setDescription("This is my first notification 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver
                .mConnectivityReceiverListener = listener;
    }
}
