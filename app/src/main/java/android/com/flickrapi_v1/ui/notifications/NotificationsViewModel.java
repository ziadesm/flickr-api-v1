package android.com.flickrapi_v1.ui.notifications;
import android.com.flickrapi_v1.pagination.PhotoDataSource;
import android.com.flickrapi_v1.pagination.PhotoDataSourceFactory;
import android.com.flickrapi_v1.pojo._PhotoModel;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NotificationsViewModel extends ViewModel {

    private static final String TAG = "Ziad Notifications";

    PhotoDataSourceFactory photoDataSourceFactory;
    MutableLiveData<PhotoDataSource> dataSourceMutableLiveData;
    LiveData<PagedList<_PhotoModel.Photos.Photo>> pagedListLiveData;

    public NotificationsViewModel() {

        photoDataSourceFactory = new PhotoDataSourceFactory();
        dataSourceMutableLiveData = photoDataSourceFactory.getLiveData();

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setPageSize(100)
                .build();
        pagedListLiveData = (new LivePagedListBuilder<Integer, _PhotoModel.Photos.Photo>(photoDataSourceFactory,config))
                .build();
    }

    public LiveData<PagedList<_PhotoModel.Photos.Photo>> getPagedListLiveData() {
        return pagedListLiveData;
    }
}