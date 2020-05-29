package echomachine.com.flickrapi_v1;
import android.app.Application;

import echomachine.com.flickrapi_v1.receiver.network.ConnectivityReceiver;

public class MyApp extends Application {
    private static MyApp INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static synchronized MyApp getINSTANCE(){
        return INSTANCE;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver
                .mConnectivityReceiverListener = listener;
    }
}
