package echomachine.com.flickrapi_v1.adapter.pagination;
import android.util.Log;

import echomachine.com.flickrapi_v1.interfaces.PhotoClient;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDataSource extends PageKeyedDataSource<Integer, _PhotoModel.Photos.Photo> {
    private static final String TAG = "ZiadPhoto";
    public static final int FIRST_PAGE = 1;
    Call<_PhotoModel> call;
    String text;

    public PhotoDataSource(String text) {
        this.text = text;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params
            , @NonNull LoadInitialCallback<Integer, _PhotoModel.Photos.Photo> callback) {

            if (text == null) {
                call = PhotoClient.getINSTANCE().getRecentPhotoPage(FIRST_PAGE);
            } else {
                call = PhotoClient.getINSTANCE().getPhotoSearchPage(text, FIRST_PAGE);
            }
            call
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
            if (text == null) {
                call = PhotoClient.getINSTANCE().getRecentPhotoPage(params.key);
            } else {
                call = PhotoClient.getINSTANCE().getPhotoSearchPage(text, params.key);
            }
            call
                .enqueue(new Callback<_PhotoModel>() {
            @Override
            public void onResponse(Call<_PhotoModel> call, Response<_PhotoModel> response) {
                List<_PhotoModel.Photos.Photo> photo = response.body().getPhotos().getPhoto();
                Log.d(TAG, "onResponse Params Key: " + params.key);
                Log.d(TAG, "onResponse Photo Size: " + photo.size());
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