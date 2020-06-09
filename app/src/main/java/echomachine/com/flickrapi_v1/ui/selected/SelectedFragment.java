package echomachine.com.flickrapi_v1.ui.selected;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import echomachine.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.receiver.network.ConnectivityReceiver;

public class SelectedFragment extends Fragment {

    private static final String TAG = "ZiadSelected";
    private SelectedViewModel viewModel;
    private NavController navController;
    private FloatingActionButton mChooseFab, mDownloadFab, mShareFab, mWallpaperFab;
    private ImageView mImageViewer;
    private RecyclerView mRecycler;
    private TextView mTagsTextView;

    public SelectedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        OnBackPressedCallback callback = new OnBackPressedCallback(true/* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selected, container, false);

        mChooseFab = view.findViewById(R.id.selected_floating_action_btn);
        mDownloadFab = view.findViewById(R.id.selected_download_floating_action_btn);
        mShareFab = view.findViewById(R.id.selected_share_floating_action_btn);
        mWallpaperFab = view.findViewById(R.id.selected_set_as_wallpaper_floating_action_btn);
        mImageViewer = view.findViewById(R.id.selected_image_header);
        mRecycler = view.findViewById(R.id.selected_recycler_view);
        mTagsTextView = view.findViewById(R.id.selected_tags_appear);

        viewModel = ViewModelProviders
                .of(this).get(SelectedViewModel.class);

        return view;
    }
}