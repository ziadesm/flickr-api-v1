package echomachine.com.flickrapi_v1.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface DaoPhoto {
    @Query("Select * from liked_photo")
    LiveData<List<Photo>> getPhotoLikedList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPhoto(Photo photo);

    @Delete
    Completable deletePhoto(Photo photo);

    @Query("DELETE FROM liked_photo")
    Completable deleteAllPhoto();
}