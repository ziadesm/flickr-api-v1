package android.com.flickrapi_v1.ui.home;
import android.com.flickrapi_v1.adapter.pagination.PhotoDataSource;
import android.com.flickrapi_v1.adapter.pagination.PhotoDataSourceFactory;
import android.com.flickrapi_v1.interfaces.PhotoClient;
import android.com.flickrapi_v1.pojo._PhotoModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.Calendar;

import retrofit2.Call;

import static android.com.flickrapi_v1.adapter.pagination.PhotoDataSource.FIRST_PAGE;

public class HomeViewModel extends ViewModel {
    PhotoDataSourceFactory factory;
    MutableLiveData<PhotoDataSource> dataSourceMutableLiveData;
    LiveData<PagedList<_PhotoModel.Photos.Photo>> mutableData;

    public HomeViewModel() {
        factory = new PhotoDataSourceFactory(getRecentPhotoPage(FIRST_PAGE));
        dataSourceMutableLiveData = factory.getLiveData();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(100)
                .build();

        mutableData = (new LivePagedListBuilder<Integer, _PhotoModel.Photos.Photo>(factory, config)).build();
    }

    public HomeViewModel(String text) {
        factory = new PhotoDataSourceFactory(getPhotoSearchPage(text, FIRST_PAGE));
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