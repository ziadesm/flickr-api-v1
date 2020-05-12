package android.com.flickrapi_v1.pagination;
import android.com.flickrapi_v1.interfaces.FlickrApiConfig;
import android.com.flickrapi_v1.interfaces.PhotoClient;
import android.com.flickrapi_v1.pojo._PhotoModel;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoDataSource extends PageKeyedDataSource<Integer, _PhotoModel.Photos.Photo> {

    private static final String TAG = "ZiadPhotoData";
    FlickrApiConfig apiConfig;
    private final String API_KEY = "931e087ffc5f1a9f9d4753ae0f14c7cd";
    private final String JSON_FORMAT = "json";
    private final String URL_S = "url_s";
    private final int NOJSON_CALLBACK = 1;
    private final int FIRST_PAGE = 1;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer,
                            _PhotoModel.Photos.Photo> callback) {


        apiConfig = RetrofitClientInstance.getRetrofitInstance().create(FlickrApiConfig.class);
        Call<_PhotoModel> recentPhotoPage = apiConfig.getRecentPhotoPage("flickr.photos.getRecent",
                API_KEY,
                URL_S,
                FIRST_PAGE,
                JSON_FORMAT,
                NOJSON_CALLBACK);
        recentPhotoPage.enqueue(new Callback<_PhotoModel>() {
            @Override
            public void onResponse(Call<_PhotoModel> call, Response<_PhotoModel> response) {
                List<_PhotoModel.Photos.Photo> photo = response.body().getPhotos().getPhoto();
                Log.d(TAG, "onResponse: " + photo);
                callback.onResult(photo, null, FIRST_PAGE + 1);
            }

            @Override
            public void onFailure(Call<_PhotoModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer,
                           _PhotoModel.Photos.Photo> callback) {


    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer,
                          _PhotoModel.Photos.Photo> callback) {
        apiConfig = RetrofitClientInstance.getRetrofitInstance().create(FlickrApiConfig.class);
        apiConfig.getRecentPhotoPage("flickr.photos.getRecent",
                API_KEY,
                URL_S,
                params.key,
                JSON_FORMAT,
                NOJSON_CALLBACK).enqueue(new Callback<_PhotoModel>() {
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
}