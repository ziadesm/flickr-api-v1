package android.com.flickrapi_v1.ui.home;
import android.com.flickrapi_v1.adapter.RecentImageAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.com.flickrapi_v1.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView recycler;
    RecentImageAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getRecentPhotos();

        recycler = root.findViewById(R.id.recycler);
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new RecentImageAdapter();
        recycler.setAdapter(adapter);

        homeViewModel.mutableData.observe(getActivity(), c -> adapter.setList(c));

        return root;
    }
}
