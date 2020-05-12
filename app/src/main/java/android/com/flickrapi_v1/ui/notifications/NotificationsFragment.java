package android.com.flickrapi_v1.ui.notifications;

import android.com.flickrapi_v1.pagination.PhotoAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.com.flickrapi_v1.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = root.findViewById(R.id.recycler_notify);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setHasFixedSize(true);

        photoAdapter = new PhotoAdapter();

        notificationsViewModel.getPagedListLiveData().observe(getActivity(), photos -> {
            photoAdapter.submitList(photos);
        });

        recyclerView.setAdapter(photoAdapter);
        return root;
    }
}
