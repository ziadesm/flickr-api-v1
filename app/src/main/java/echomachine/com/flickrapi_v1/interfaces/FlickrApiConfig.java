package echomachine.com.flickrapi_v1.interfaces;
import echomachine.com.flickrapi_v1.pojo.SelectedPhotos;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApiConfig {

    @GET("rest/")
    Single<_PhotoModel> getRecentPhotoPage(
            @Query("method") String method
            ,@Query("api_key") String api_key
            ,@Query("extras") String extras
            ,@Query("page") int page
            ,@Query("format") String format
            ,@Query("nojsoncallback") int nojsoncallback);

    @GET("rest/")
    Single<_PhotoModel> getPhotoSearchPage(
            @Query("method") String method
            ,@Query("api_key") String api_key
            ,@Query("text") String text
            ,@Query("extras") String extras
            ,@Query("page") int page
            ,@Query("format") String format
            ,@Query("nojsoncallback") int nojsoncallback);

    @GET("rest/")
    Call<SelectedPhotos> getProfilePhotos(
            @Query("method") String method
            ,@Query("api_key") String api_key
            ,@Query("user_id") String user_id
            ,@Query("extras") String extras
            ,@Query("format") String format
            ,@Query("nojsoncallback") int nojsoncallback);
}