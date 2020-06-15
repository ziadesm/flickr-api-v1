package echomachine.com.flickrapi_v1.adapter.pagination;
import android.annotation.SuppressLint;
import android.util.Log;

import echomachine.com.flickrapi_v1.interfaces.PhotoClient;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PhotoDataSource extends PageKeyedDataSource<Integer, _PhotoModel.Photos.Photo> {
    private static final String TAG = "ZiadPhoto";
    public static final int FIRST_PAGE = 1;
    Single<_PhotoModel> call;
    String text;

    public PhotoDataSource(String text) {
        this.text = text;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params
            , @NonNull LoadInitialCallback<Integer, _PhotoModel.Photos.Photo> callback) {

            if (text == null) {
                call = PhotoClient.getINSTANCE().getRecentPhotoPage(FIRST_PAGE);
            } else {
                call = PhotoClient.getINSTANCE().getPhotoSearchPage(text, FIRST_PAGE);
                Log.d(TAG, "" + text);
            }

            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(photoModel -> {
                        List<_PhotoModel.Photos.Photo> photo = photoModel.getPhotos().getPhoto();
                        callback.onResult(photo, null, FIRST_PAGE + 1);
                    }, e -> Log.d(TAG, "" + e.getMessage()));
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params
            , @NonNull LoadCallback<Integer, _PhotoModel.Photos.Photo> callback) {
            if (text == null) {
                call = PhotoClient.getINSTANCE().getRecentPhotoPage(params.key);
            } else {
                call = PhotoClient.getINSTANCE().getPhotoSearchPage(text, params.key);
            }
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(photoModel -> {
                        List<_PhotoModel.Photos.Photo> photo = photoModel.getPhotos().getPhoto();
                        Log.d(TAG, "onResponse Params Key: " + params.key);
                        Log.d(TAG, "onResponse Photo Size: " + photo.size());
                        callback.onResult(photo, params.key + 1);
                    }, e -> Log.d(TAG, "" + e.getMessage()));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params
            , @NonNull LoadCallback<Integer, _PhotoModel.Photos.Photo> callback) {

    }
}