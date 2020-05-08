package android.com.flickrapi_v1.ui.home;
import android.com.flickrapi_v1.adapter.RecentImageAdapter;
import android.com.flickrapi_v1.receiver.NetworkChangeReceiver;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
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

public class HomeFragment extends Fragment {

    private BroadcastReceiver rReceiver = new NetworkChangeReceiver();
    private HomeViewModel homeViewModel;
    RecyclerView aRecycler;
    RecentImageAdapter aAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getRecentPhotos();

        aRecycler = root.findViewById(R.id.recycler);
        aRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        aAdapter = new RecentImageAdapter();
        aRecycler.setAdapter(aAdapter);

        homeViewModel.mutableData.observe(getActivity(), c -> aAdapter.setList(c));

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(rReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(rReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
