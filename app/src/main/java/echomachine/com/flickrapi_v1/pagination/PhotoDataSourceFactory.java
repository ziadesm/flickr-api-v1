package android.com.flickrapi_v1.pagination;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PhotoDataSourceFactory extends DataSource.Factory {
    PhotoDataSource dataSource;
    MutableLiveData<PhotoDataSource> liveData;

    public PhotoDataSourceFactory() {
        liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        dataSource = new PhotoDataSource();
        liveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<PhotoDataSource> getLiveData() {
        return liveData;
    }
}
