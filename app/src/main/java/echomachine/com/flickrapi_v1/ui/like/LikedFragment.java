package echomachine.com.flickrapi_v1.ui.like;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import echomachine.com.flickrapi_v1.R;

import echomachine.com.flickrapi_v1.adapter.LikedPhotoAdapter;
import echomachine.com.flickrapi_v1.adapter.SimpleRecyclerItemTouchHelper;
import echomachine.com.flickrapi_v1.data.RepositoryPhoto;

public class LikedFragment extends Fragment {
    private static final String TAG = "ZiadLiked";
    private RepositoryPhoto repo;
    private RecyclerView recyclerView;
    private LikedPhotoAdapter adapter;
    private NavController navController;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked, container, false);
        recyclerView = view.findViewById(R.id.recycler_liked_photo);
        repo = new RepositoryPhoto(getContext());
        adapter = new LikedPhotoAdapter(getContext(), this);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        repo.getAllLikedPhoto().observe(getActivity(), c -> adapter.setList(c));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleRecyclerItemTouchHelper(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }
}