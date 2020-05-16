package android.com.flickrapi_v1.ui.home;

import android.com.flickrapi_v1.interfaces.PhotoClient;
import android.com.flickrapi_v1.pojo._PhotoModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModelSearch extends ViewModel {
    MutableLiveData<List<_PhotoModel.Photos.Photo>> data = new MutableLiveData();

    public void getPhotoSearch(String text) {
        Single<_PhotoModel> photoSearch = PhotoClient.getINSTANCE().getPhotoSearch(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        photoSearch.subscribe(c -> {
            data.setValue(c.getPhotos().getPhoto());
        });
    }
}
