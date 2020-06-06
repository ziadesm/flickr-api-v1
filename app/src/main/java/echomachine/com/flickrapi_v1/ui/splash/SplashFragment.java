package echomachine.com.flickrapi_v1.ui.splash;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import echomachine.com.flickrapi_v1.R;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashFragment extends Fragment {

    private ImageView logoIV;
    private TextView poweredText;
    private ProgressBar progressBar;
    private NavController navController;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        logoIV = view.findViewById(R.id.splash_logo_flickr);
        poweredText = view.findViewById(R.id.splash_text_powered);
        progressBar = view.findViewById(R.id.splash_progress_line);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_logo_transition);

        poweredText.startAnimation(animation);
        logoIV.startAnimation(animation);

        new CountDownTimer(2200, 550) {

            int progress = 50;
            @Override
            public void onTick(long millisUntilFinished) {
                progressBar.setProgress(progress);
                progress = progress + 25;
            }

            @Override
            public void onFinish() {
                navController.navigate(R.id.navigation_home);
            }
        }.start();

        return view;
    }
}