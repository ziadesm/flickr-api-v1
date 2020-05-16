package android.com.flickrapi_v1.ui.home;
import android.com.flickrapi_v1.adapter.pagination.PhotoDataSource;
import android.com.flickrapi_v1.adapter.pagination.PhotoDataSourceFactory;
import android.com.flickrapi_v1.pojo._PhotoModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "Ziad Home";
    PhotoDataSourceFactory factory;
    MutableLiveData<PhotoDataSource> dataSourceMutableLiveData;
    LiveData<PagedList<_PhotoModel.Photos.Photo>> mutableData;

    public HomeViewModel() {
        factory = new PhotoDataSourceFactory();
        dataSourceMutableLiveData = factory.getLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(100)
                .build();

        mutableData = (new LivePagedListBuilder<Integer, _PhotoModel.Photos.Photo>(factory, config)).build();
    }

    public LiveData<PagedList<_PhotoModel.Photos.Photo>> getMutableData() {
        return mutableData;
    }
}