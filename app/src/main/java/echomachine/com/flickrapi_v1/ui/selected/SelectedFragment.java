package echomachine.com.flickrapi_v1.ui.selected;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import echomachine.com.flickrapi_v1.R;

public class SelectedFragment extends Fragment {

    private static final String TAG = "ZiadSelected";
    private SelectedViewModel viewModel;
    private NavController navController;
    private FloatingActionButton mChooseFab, mDownloadFab, mShareFab, mWallpaperFab;
    private Animation aOpenFabs, aCloseFabs, aOpenRotate, aCloseRotate;
    private ImageView mImageViewer;
    private RecyclerView mRecycler;
    private TextView mTagsTextView;
    private boolean isOpen = false;


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

        // Animation initialised here
        aOpenFabs = AnimationUtils.loadAnimation(getContext(), R.anim.anim_fab_opens);
        aCloseFabs = AnimationUtils.loadAnimation(getContext(), R.anim.anim_fab_closes);
        aOpenRotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_open_main_fab);
        aCloseRotate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rotate_close_main_fab);

        viewModel = ViewModelProviders
                .of(this).get(SelectedViewModel.class);

        mChooseFab.setOnClickListener(v -> {
          if (isOpen) {
              closeFabToCloseFabs();
              isOpen = false;
          } else {
              openFabToOpenFabs();
              isOpen = true;
          }
        });

        return view;
    }

    private void openFabToOpenFabs() {
        mChooseFab.startAnimation(aOpenRotate);

        mShareFab.startAnimation(aOpenFabs);
        mWallpaperFab.startAnimation(aOpenFabs);
        mDownloadFab.startAnimation(aOpenFabs);
    }

    private void closeFabToCloseFabs() {
        mChooseFab.startAnimation(aCloseRotate);

        mShareFab.startAnimation(aCloseFabs);
        mWallpaperFab.startAnimation(aCloseFabs);
        mDownloadFab.startAnimation(aCloseFabs);
    }
}