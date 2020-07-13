package echomachine.com.flickrapi_v1.ui.home;
import android.app.SearchManager;

import echomachine.com.flickrapi_v1.MyApp;
import echomachine.com.flickrapi_v1.adapter.RecentImageAdapter;
import echomachine.com.flickrapi_v1.receiver.network.ConnectivityReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import echomachine.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.utilties.HelperMethods;
import android.widget.SearchView;

import static android.content.Context.SEARCH_SERVICE;

public class HomeFragment extends Fragment implements
        ConnectivityReceiver.ConnectivityReceiverListener {
    private HomeViewModel homeViewModel;
    RecyclerView aRecycler;
    private RecentImageAdapter aAdapter;
    private SwipeRefreshLayout refreshLayout;
    SearchView searchView = null;
    SearchView.OnQueryTextListener listener;
    NavController navController;
    ConnectivityReceiver receiver;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        aRecycler = root.findViewById(R.id.recycler);
        refreshLayout = root.findViewById(R.id.home_fragment_layout);

        refreshLayout.setColorSchemeResources(R.color.dialog_background_pri
                , R.color.colorPrimaryDark
                , R.color.dialog_surface);

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        aAdapter = new RecentImageAdapter(getContext(), this);
        refreshLayout.setOnRefreshListener(() -> {
            homeViewModel = new HomeViewModel();
            homeViewModel.mutableData.observe(getActivity(), photos -> {
                aAdapter.submitList(photos);
            });
            refreshLayout.setRefreshing(false);
        });

        aRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        homeViewModel.getMutableData().observe(getActivity(), photos -> {
            aAdapter.submitList(photos);
        });

        aRecycler.setAdapter(aAdapter);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        checkInternetConnection();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

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
                    if (query.length() != 0) {
                        homeViewModel = new HomeViewModel(query);
                    } else {
                        homeViewModel = new HomeViewModel();
                    }
                    homeViewModel.mutableDataSearch
                            .observe(getActivity(), photos -> aAdapter.submitList(photos));
                    HelperMethods.disappearKeypad(getActivity(), getView());
//                    InputMethodManager inputManager = (InputMethodManager) getContext()
//                            .getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken()
//                            ,InputMethodManager.HIDE_NOT_ALWAYS);
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

    @Override
    public void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        receiver = new ConnectivityReceiver();
        getActivity().registerReceiver(receiver, intentFilter);

        MyApp.getINSTANCE().setConnectivityListener(this);
    }

    private void checkInternetConnection() {
        boolean connected = ConnectivityReceiver.isConnected();
        if (!connected) {
            showOfflineFragment();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }

    private void showOfflineFragment() {
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.navigation_offline);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            showOfflineFragment();
        }
    }
}