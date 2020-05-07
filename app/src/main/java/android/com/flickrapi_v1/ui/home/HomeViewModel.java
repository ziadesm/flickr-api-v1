package android.com.flickrapi_v1.ui.home;
import android.com.flickrapi_v1.interfaces.PhotoClient;
import android.com.flickrapi_v1.pojo._PhotoModel;
import android.com.flickrapi_v1.pojo._PhotoModel.Photos;
import android.com.flickrapi_v1.pojo._PhotoModel.Photos.Photo;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private static final String TAG = "HomeViewModel";
    public MutableLiveData<_PhotoModel> mutableData = new MutableLiveData<>();;

    public void getRecentPhotos() {
        Single<_PhotoModel> recentPhotos = PhotoClient.getINSTANCE().getRecentPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        recentPhotos.subscribe(c -> mutableData.setValue(c)
                , x -> Log.d(TAG, "getRecentPhotos: " + x.getMessage()));
    }
}