package echomachine.com.flickrapi_v1.ui.home;
import android.util.Log;

import echomachine.com.flickrapi_v1.adapter.pagination.PhotoDataSource;
import echomachine.com.flickrapi_v1.adapter.pagination.PhotoDataSourceFactory;
import echomachine.com.flickrapi_v1.interfaces.PhotoClient;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import retrofit2.Call;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "ZiadHome";
    PhotoDataSourceFactory factory;
    MutableLiveData<PhotoDataSource> dataSourceMutableLiveData;
    LiveData<PagedList<_PhotoModel.Photos.Photo>> mutableData;
    LiveData<PagedList<_PhotoModel.Photos.Photo>> mutableDataSearch;

    public HomeViewModel() {
        factory = new PhotoDataSourceFactory(null);
        dataSourceMutableLiveData = factory.getLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(32)
                .setPageSize(32)
                .setEnablePlaceholders(false)
                .build();

        mutableData = (new LivePagedListBuilder<Integer, _PhotoModel.Photos.Photo>(factory, config)).build();
    }

    public HomeViewModel(String text) {
        factory = new PhotoDataSourceFactory(text);
        dataSourceMutableLiveData = factory.getLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(32)
                .setPageSize(32)
                .setEnablePlaceholders(false)
                .build();

        Log.d(TAG, "" + config.pageSize);

        mutableDataSearch = (new LivePagedListBuilder<Integer, _PhotoModel.Photos.Photo>(factory, config)).build();
    }

    public LiveData<PagedList<_PhotoModel.Photos.Photo>> getMutableData() {
        return mutableData;
    }

    public LiveData<PagedList<_PhotoModel.Photos.Photo>> getMutableDataSearch() {
        return mutableDataSearch;
    }
}