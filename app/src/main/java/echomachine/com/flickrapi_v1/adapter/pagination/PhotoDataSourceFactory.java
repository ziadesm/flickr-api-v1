package echomachine.com.flickrapi_v1.adapter.pagination;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PhotoDataSourceFactory extends DataSource.Factory {
    PhotoDataSource photoDataSource;
    MutableLiveData<PhotoDataSource> liveData;
    String text;

    public PhotoDataSourceFactory(String text) {
        this.liveData = new MutableLiveData<>();
        this.text = text;
    }

    @Override
    public DataSource create() {
        photoDataSource = new PhotoDataSource(text);
        liveData.postValue(photoDataSource);
        return photoDataSource;
    }

    public MutableLiveData<PhotoDataSource> getLiveData() {
        return liveData;
    }
}