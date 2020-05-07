package android.com.flickrapi_v1.interfaces;

import android.com.flickrapi_v1.pojo._PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoClient {
    FlickrApiConfig apiConfig;
    public static PhotoClient INSTANCE;
    private final String BASE_URL = "https://www.flickr.com/services/";
    private final String API_KEY = "931e087ffc5f1a9f9d4753ae0f14c7cd";

    public PhotoClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiConfig = retrofit.create(FlickrApiConfig.class);
    }

    public static PhotoClient getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new PhotoClient();
        }
        return INSTANCE;
    }

    public Call<List<_PhotoModel.Photos.Photo>> getRecentPhotos() {
        return apiConfig.getRecentPhoto("flickr.photos.getRecent", API_KEY);
    }
}
