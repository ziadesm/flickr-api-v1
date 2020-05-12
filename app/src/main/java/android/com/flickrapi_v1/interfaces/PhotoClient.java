package android.com.flickrapi_v1.interfaces;

import android.com.flickrapi_v1.pojo._PhotoModel;

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

    public Single<_PhotoModel> getRecentPhotos() {
        return apiConfig.getRecentPhoto("flickr.photos.getRecent",
                API_KEY,
                URL_S,
                JSON_FORMAT,
                NOJSON_CALLBACK);
    }

    public Call<_PhotoModel> getRecentPhotoPage(int page_num) {
        return apiConfig.getRecentPhotoPage("flickr.photos.getRecent",
                API_KEY,
                URL_S,
                page_num,
                JSON_FORMAT,
                NOJSON_CALLBACK);
    }
}
