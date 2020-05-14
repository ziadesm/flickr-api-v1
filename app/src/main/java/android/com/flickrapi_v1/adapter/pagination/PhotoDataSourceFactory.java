package android.com.flickrapi_v1.adapter.pagination;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PhotoDataSourceFactory extends DataSource.Factory {
    PhotoDataSource photoDataSource;
    MutableLiveData<PhotoDataSource> liveData;

    public PhotoDataSourceFactory() {
        this.liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        photoDataSource = new PhotoDataSource();
        liveData.postValue(photoDataSource);
        return photoDataSource;
    }

    public MutableLiveData<PhotoDataSource> getLiveData() {
        return liveData;
    }
}
