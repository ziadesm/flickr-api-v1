package echomachine.com.flickrapi_v1.interfaces;

import echomachine.com.flickrapi_v1.pojo.SelectedPhotos;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;
import android.util.Log;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoClient {
    FlickrApiConfig apiConfig;
    public static PhotoClient INSTANCE;
    private final String BASE_URL = "https://www.flickr.com/services/";
    private final String API_KEY = "931e087ffc5f1a9f9d4753ae0f14c7cd";
    private final String JSON_FORMAT = "json";
    private final String URL_S = "url_s";
    private final int NOJSON_CALLBACK = 1;
    private static final String TAG = "Ziad PhotoClient";

    public PhotoClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiConfig = retrofit.create(FlickrApiConfig.class);
    }

    public static PhotoClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PhotoClient();
        }
        return INSTANCE;
    }

    public Single<_PhotoModel> getRecentPhotoPage(int page) {
        Log.d(TAG, "getRecentPhotoPage: " + page);
        return apiConfig.getRecentPhotoPage("flickr.photos.getRecent"
                , API_KEY, URL_S, page, JSON_FORMAT, NOJSON_CALLBACK);
    }

    public Single<_PhotoModel> getPhotoSearchPage(String text, int page) {
        return apiConfig.getPhotoSearchPage("flickr.photos.search"
                , API_KEY, text, URL_S, page, JSON_FORMAT, NOJSON_CALLBACK);
    }

    public Call<SelectedPhotos> getProfilePhotosPage(String user_id) {
        return apiConfig.getProfilePhotos("flickr.people.getPhotos"
                , API_KEY, user_id, URL_S, JSON_FORMAT, NOJSON_CALLBACK);
    }
}
