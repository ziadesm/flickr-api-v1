package echomachine.com.flickrapi_v1.ui.profile;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.com.flickrapi_v1.R;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FirebaseAuth auth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        return root;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            Fragment registerFragment = new RegisterFragment();
            FragmentTransaction fm = getParentFragmentManager().beginTransaction();
            fm.replace(R.id.nav_host_fragment, registerFragment);
            fm.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fm.remove(this);
            fm.commit();
        }
        super.onAttach(context);
    }
}