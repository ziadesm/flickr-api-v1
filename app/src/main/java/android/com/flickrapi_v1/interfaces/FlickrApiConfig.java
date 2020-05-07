package android.com.flickrapi_v1.interfaces;

import android.com.flickrapi_v1.pojo._PhotoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlickrApiConfig {

    @GET("rest/?method={method}&api_key={api_key}&format=json&nojsoncallback=1")
    Call<List<_PhotoModel.Photos.Photo>> getRecentPhoto(@Path("method") String method
            , @Path("api_key") String api_key);

    @GET("rest/?method={method}&api_key={api_key}&page={page_num}&format=json&nojsoncallback=1")
    Call<List<_PhotoModel.Photos.Photo>> getRecentPhoto(@Path("method") String method
            , @Path("api_key") String api_key
            , @Path("page_num") int page_num);
}
