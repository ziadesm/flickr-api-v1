package android.com.flickrapi_v1.interfaces;

import android.com.flickrapi_v1.pojo._PhotoModel;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApiConfig {

    @GET("rest/")
    Single<_PhotoModel> getRecentPhoto(
            @Query("method") String method
            ,@Query("api_key") String api_key
            ,@Query("extras") String extras
            ,@Query("format") String format
            ,@Query("nojsoncallback") int nojsoncallback);

    @GET("rest/?method={method}&api_key={api_key}&page={page_num}&format=json&nojsoncallback=1")
    Call<_PhotoModel> getRecentPhoto(@Query("method") String method
            , @Query("api_key") String api_key
            , @Query("page_num") int page_num);
}
