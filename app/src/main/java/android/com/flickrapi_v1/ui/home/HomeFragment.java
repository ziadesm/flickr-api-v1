package android.com.flickrapi_v1.ui.home;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.com.flickrapi_v1.R;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView aRecycler;
    RecentImageAdapter aAdapter;
    SwipeRefreshLayout refreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        aRecycler = root.findViewById(R.id.recycler);
        refreshLayout = root.findViewById(R.id.home_fragment_layout);

        refreshLayout.setColorSchemeResources(R.color.dialog_background_pri
                , R.color.colorPrimaryDark
                , R.color.dialog_surface);

        refreshLayout.setOnRefreshListener(() -> homeViewModel.getRecentPhotos());

        scheduleJob();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.getRecentPhotos();
        aRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        aAdapter = new RecentImageAdapter();
        aRecycler.setAdapter(aAdapter);
        homeViewModel.mutableData.observe(getActivity(), c -> {
            aAdapter.setList(c);
            refreshLayout.setRefreshing(false);
        });

        return root;
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

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (cm != null) {
            activeNetworkInfo = cm.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}