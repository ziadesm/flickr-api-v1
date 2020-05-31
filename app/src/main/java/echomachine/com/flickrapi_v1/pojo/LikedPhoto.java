package echomachine.com.flickrapi_v1.pojo;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "liked_photo")
public class LikedPhoto {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "url_s")
    private String url_s;
    @ColumnInfo(name = "owner")
    private String owner;
    @ColumnInfo(name = "liked_or_not")
    private boolean liked;

    public LikedPhoto(String url_s, String owner, boolean liked) {
        this.url_s = url_s;
        this.owner = owner;
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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}