package android.com.flickrapi_v1.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.com.flickrapi_v1.R;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private boolean hasAccount = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        if (hasAccount) {
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, new RegisterFragment())
                    .commit();
        }
        return root;
    }
}