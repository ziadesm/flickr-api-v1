package echomachine.com.flickrapi_v1.adapter.pagination;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import retrofit2.Call;

public class PhotoDataSourceFactory extends DataSource.Factory {
    PhotoDataSource photoDataSource;
    MutableLiveData<PhotoDataSource> liveData;
    Call<_PhotoModel> call;

    public PhotoDataSourceFactory(Call<_PhotoModel> call) {
        this.liveData = new MutableLiveData<>();
        this.call = call;
    }

    @Override
    public DataSource create() {
        photoDataSource = new PhotoDataSource(call);
        liveData.postValue(photoDataSource);
        return photoDataSource;
    }

    public MutableLiveData<PhotoDataSource> getLiveData() {
        return liveData;
    }
}