package echomachine.com.flickrapi_v1.ui.selected;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.com.flickrapi_v1.R;

import echomachine.com.flickrapi_v1.ui.home.HomeFragment;
import echomachine.com.flickrapi_v1.ui.profile.RegisterFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SelectedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectedFragment.
     */
    public static SelectedFragment newInstance(String param1, String param2) {
        SelectedFragment fragment = new SelectedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Fragment registerFragment = new HomeFragment();
                FragmentTransaction fm = getParentFragmentManager().beginTransaction();
                fm.replace(R.id.nav_host_fragment, registerFragment);
                fm.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fm.commit();
            }
        });

        return view;
    }
}