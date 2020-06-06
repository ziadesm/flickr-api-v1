package echomachine.com.flickrapi_v1.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import echomachine.com.flickrapi_v1.pojo.LikedPhoto;
import io.reactivex.Completable;

@Dao
public interface DaoPhoto {
    @Query("Select * from liked_photo")
    LiveData<List<LikedPhoto>> getPhotoLikedList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPhoto(LikedPhoto likedPhoto);

    @Delete
    Completable deletePhoto(LikedPhoto likedPhoto);

    @Delete
    Completable deleteSomePhoto(List<LikedPhoto> photos);

    @Query("DELETE FROM liked_photo")
    Completable deleteAllPhoto();
}