package android.com.flickrapi_v1.interfaces;

import android.com.flickrapi_v1.pojo._PhotoModel;
import android.util.Log;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiConfig = retrofit.create(FlickrApiConfig.class);
    }

    public static PhotoClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PhotoClient();
        }
        return INSTANCE;
    }

    public Call<_PhotoModel> getRecentPhotoPage(int page) {
        Log.d(TAG, "getRecentPhotoPage: " + page);
        return apiConfig.getRecentPhotoPage("flickr.photos.getRecent", API_KEY, URL_S, page, JSON_FORMAT, NOJSON_CALLBACK);
    }
}
