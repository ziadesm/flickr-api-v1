package echomachine.com.flickrapi_v1.data;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "liked_photo")
public class Photo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "url_s")
    private String url_s;
    @ColumnInfo(name = "user_id")
    private String user_id;
    @ColumnInfo(name = "liked_or_not")
    private boolean liked;

    public Photo(String url_s, String user_id, boolean liked) {
        this.url_s = url_s;
        this.user_id = user_id;
        this.liked = liked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl_s() {
        return url_s;
    }

    public void setUrl_s(String url_s) {
        this.url_s = url_s;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}