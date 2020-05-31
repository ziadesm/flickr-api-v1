package echomachine.com.flickrapi_v1.data;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import echomachine.com.flickrapi_v1.pojo.LikedPhoto;

@Database(entities = LikedPhoto.class, exportSchema = false, version = 2)
public abstract class PhotoDatabase extends RoomDatabase {
    private static final String DB_NAME = "like_photo_db";
    private static PhotoDatabase INSTANCE;
    public abstract DaoPhoto dao();

    public static synchronized PhotoDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhotoDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}