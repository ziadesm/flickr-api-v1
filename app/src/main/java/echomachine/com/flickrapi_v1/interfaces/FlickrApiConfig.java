package echomachine.com.flickrapi_v1.interfaces;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrApiConfig {

    @GET("rest/")
    Call<_PhotoModel> getRecentPhotoPage(
            @Query("method") String method
            ,@Query("api_key") String api_key
            ,@Query("extras") String extras
            ,@Query("page") int page
            ,@Query("format") String format
            ,@Query("nojsoncallback") int nojsoncallback);

    @GET("rest/")
    Call<_PhotoModel> getPhotoSearchPage(
            @Query("method") String method
            ,@Query("api_key") String api_key
            ,@Query("text") String text
            ,@Query("extras") String extras
            ,@Query("page") int page
            ,@Query("format") String format
            ,@Query("nojsoncallback") int nojsoncallback);

    @GET("rest/")
    Call<_PhotoModel> getProfilePhotosPage(
            @Query("method") String method
            ,@Query("api_key") String api_key
            ,@Query("user_id") String user_id
            ,@Query("extras") String extras
            ,@Query("page") int page
            ,@Query("format") String format
            ,@Query("nojsoncallback") int nojsoncallback);
}