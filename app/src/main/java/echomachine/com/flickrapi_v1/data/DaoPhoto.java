package echomachine.com.flickrapi_v1.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DaoPhoto {
    @Query("Select * from liked_photo")
    LiveData<List<Photo>> getPhotoLikedList();

    @Insert
    void insertPhoto(Photo photo);

    @Delete
    void deletePhoto(Photo photo);
}