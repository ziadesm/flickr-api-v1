package echomachine.com.flickrapi_v1.ui.selected;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.com.flickrapi_v1.R;

import echomachine.com.flickrapi_v1.ui.home.HomeFragment;
import echomachine.com.flickrapi_v1.ui.profile.RegisterFragment;

public class SelectedFragment extends Fragment {
    private NavController navController;

    public SelectedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navController.popBackStack();
    }
}