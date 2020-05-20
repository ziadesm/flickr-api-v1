package android.com.flickrapi_v1.adapter.pagination;
import android.com.flickrapi_v1.interfaces.PhotoClient;
import android.com.flickrapi_v1.pojo._PhotoModel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDataSource extends PageKeyedDataSource<Integer, _PhotoModel.Photos.Photo> {
    public static final int FIRST_PAGE = 1;
    private Call<_PhotoModel> callbackRet;

    public PhotoDataSource(Call<_PhotoModel> callback) {
        this.callbackRet = callback;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params
            , @NonNull LoadInitialCallback<Integer, _PhotoModel.Photos.Photo> callback) {

            callbackRet
                .enqueue(new Callback<_PhotoModel>() {
            @Override
            public void onResponse(Call<_PhotoModel> call, Response<_PhotoModel> response) {
                List<_PhotoModel.Photos.Photo> photo = response.body().getPhotos().getPhoto();
                callback.onResult(photo, null, FIRST_PAGE + 1);
            }

            @Override
            public void onFailure(Call<_PhotoModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params
            , @NonNull LoadCallback<Integer, _PhotoModel.Photos.Photo> callback) {

        PhotoClient.getINSTANCE().getRecentPhotoPage(params.key)
                .enqueue(new Callback<_PhotoModel>() {
            @Override
            public void onResponse(Call<_PhotoModel> call, Response<_PhotoModel> response) {
                List<_PhotoModel.Photos.Photo> photo = response.body().getPhotos().getPhoto();
                callback.onResult(photo, params.key + 1);
            }

            @Override
            public void onFailure(Call<_PhotoModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params
            , @NonNull LoadCallback<Integer, _PhotoModel.Photos.Photo> callback) {

    }
}