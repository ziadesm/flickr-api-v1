package echomachine.com.flickrapi_v1.ui.profile;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import echomachine.com.flickrapi_v1.R;

import com.google.firebase.auth.FirebaseAuth;

import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_EMAIL_PREF;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_NAME_PREF;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_PHONE_PREF;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_USER_PHONE;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FirebaseAuth auth;
    private NavController navController;
    private TextView emailTv, phoneTv, nameTv;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        emailTv = root.findViewById(R.id.profile_email_tv);
        phoneTv = root.findViewById(R.id.profile_phone_tv);
        nameTv = root.findViewById(R.id.profile_name_tv);

        displayNamePhone();

        return root;
    }

    private void displayNamePhone() {
        SharedPreferences preferences = getContext()
                .getSharedPreferences(KEY_SAVE_USER_PHONE, Context.MODE_PRIVATE);
        String name = preferences.getString(KEY_SAVE_NAME_PREF, null);
        String phone = preferences.getString(KEY_SAVE_PHONE_PREF, null);
        String email = preferences.getString(KEY_SAVE_EMAIL_PREF, null);

        phoneTv.setText(phone);
        nameTv.setText(name);
        emailTv.setText(email);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        if (auth.getCurrentUser() == null) {
            navController.navigate(R.id.navigation_register);
        }
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