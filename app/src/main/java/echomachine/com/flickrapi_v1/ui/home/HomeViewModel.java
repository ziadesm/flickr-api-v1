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

import static echomachine.com.flickrapi_v1.adapter.pagination.PhotoDataSource.FIRST_PAGE;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "ZiadHome";
    PhotoDataSourceFactory factory;
    MutableLiveData<PhotoDataSource> dataSourceMutableLiveData;
    LiveData<PagedList<_PhotoModel.Photos.Photo>> mutableData;

    public HomeViewModel() {
        factory = new PhotoDataSourceFactory(getRecentPhotoPage(FIRST_PAGE));
        Log.d(TAG, "HomeViewModel: " + 2);
        dataSourceMutableLiveData = factory.getLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(100)
                .build();

        mutableData = (new LivePagedListBuilder<Integer, _PhotoModel.Photos.Photo>(factory, config)).build();
    }

    public HomeViewModel(String text) {
        if (text != null) {
            factory = new PhotoDataSourceFactory(getPhotoSearchPage(text, FIRST_PAGE));
        }
        Log.d(TAG, "HomeViewModel: " + text + 2);
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

    public Call<_PhotoModel> getRecentPhotoPage(int page) {
        return PhotoClient.getINSTANCE().getRecentPhotoPage(page);
    }

    public Call<_PhotoModel> getPhotoSearchPage(String text, int page) {
        return PhotoClient.getINSTANCE().getPhotoSearchPage(text, page);
    }
}