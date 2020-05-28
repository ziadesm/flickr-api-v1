package echomachine.com.flickrapi_v1.data;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepositoryPhoto {
    LiveData<List<Photo>> allLikedPhoto;
    private DaoPhoto daoPhoto;

    public RepositoryPhoto(Context context) {
        PhotoDatabase database = PhotoDatabase.getInstance(context);
        daoPhoto = database.dao();
        allLikedPhoto = daoPhoto.getPhotoLikedList();
    }

    public Completable insertPhoto(Photo photo) {
        return daoPhoto.insertPhoto(photo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deletePhoto(Photo photo) {
        return daoPhoto.deletePhoto(photo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteAllPhoto() {
        return daoPhoto.deleteAllPhoto().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<List<Photo>> getAllLikedPhoto() {
        return allLikedPhoto;
    }
}
