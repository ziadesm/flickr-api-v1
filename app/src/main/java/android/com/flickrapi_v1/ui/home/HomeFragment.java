package android.com.flickrapi_v1.ui.home;
import android.app.SearchManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.com.flickrapi_v1.adapter.RecentImageAdapter;
import android.com.flickrapi_v1.receiver.network.NetworkSchedulerService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.com.flickrapi_v1.R;
import android.widget.SearchView;

import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static android.content.Context.SEARCH_SERVICE;

public class HomeFragment extends Fragment {

    private static final String TAG = "ZiadHomeFragment";
    private HomeViewModel homeViewModel;
    RecyclerView aRecycler;
    RecentImageAdapter aAdapter;
    SwipeRefreshLayout refreshLayout;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        aRecycler = root.findViewById(R.id.recycler);
        refreshLayout = root.findViewById(R.id.home_fragment_layout);
        refreshLayout.setColorSchemeResources(R.color.dialog_background_pri
                , R.color.colorPrimaryDark
                , R.color.dialog_surface);

        scheduleJob();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        aAdapter = new RecentImageAdapter();
        refreshLayout.setOnRefreshListener(() -> {
            homeViewModel = new HomeViewModel();
            homeViewModel.getMutableData().observe(getActivity(), photos -> {
                aAdapter.submitList(photos);
                refreshLayout.setEnabled(false);
            });
        });

        aRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        aRecycler.setHasFixedSize(true);

        homeViewModel.getMutableData().observe(getActivity(), photos -> {
            aAdapter.submitList(photos);
        });

        aRecycler.setAdapter(aAdapter);

        homeViewModel.mutableData.observe(getActivity(), c -> {
            aAdapter.submitList(c);
            refreshLayout.setRefreshing(false);
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_icon_bar, menu);
        MenuItem item = menu.findItem(R.id.search_icon_bar);
        SearchManager manager = (SearchManager) getActivity().getSystemService(SEARCH_SERVICE);

        if (item != null) {
            searchView = (SearchView) item.getActionView();
            searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
            listener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(listener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (cm != null) {
            activeNetworkInfo = cm.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(getActivity(), NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), NetworkSchedulerService.class);
        getActivity().startService(intent);
    }

    @Override
    public void onStop() {
        getActivity().stopService(new Intent(getActivity(), NetworkSchedulerService.class));
        super.onStop();
    }
}