package echomachine.com.flickrapi_v1.ui.home;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import echomachine.com.flickrapi_v1.MyApp;
import echomachine.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.receiver.network.ConnectivityReceiver;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

public class OfflineFragment extends Fragment
        implements ConnectivityReceiver.ConnectivityReceiverListener {

    private NavController navController;
    private Button mBtnoffline;
    private ProgressBar bar;
    private ConnectivityReceiver receiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        mBtnoffline = view.findViewById(R.id.offline_button);
        bar = view.findViewById(R.id.offline_progress_bar);

        mBtnoffline.setOnClickListener(v -> {
            if (isOnline()) {
                navController.navigate(R.id.navigation_home);
            } else {
                bar.setVisibility(View.VISIBLE);
                new CountDownTimer(2000, 2000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        bar.setVisibility(View.INVISIBLE);
                    }
                };
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean connected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        return connected;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
            OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
                @Override
                public void handleOnBackPressed() {
                    // Handle the back button event
                    navController.navigate(R.id.navigation_home);
                }
            };
            requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        }
    }
}