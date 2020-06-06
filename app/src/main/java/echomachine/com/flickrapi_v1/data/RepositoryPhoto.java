package echomachine.com.flickrapi_v1.data;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import echomachine.com.flickrapi_v1.pojo.LikedPhoto;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepositoryPhoto {
    LiveData<List<LikedPhoto>> allLikedPhoto;
    private DaoPhoto daoPhoto;

    public RepositoryPhoto(Context context) {
        PhotoDatabase database = PhotoDatabase.getInstance(context);
        daoPhoto = database.dao();
        allLikedPhoto = daoPhoto.getPhotoLikedList();
    }

    public Completable insertPhoto(LikedPhoto likedPhoto) {
        return daoPhoto.insertPhoto(likedPhoto).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deletePhoto(LikedPhoto likedPhoto) {
        return daoPhoto.deletePhoto(likedPhoto).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteSomePhoto(List<LikedPhoto> photo) {
        return daoPhoto.deleteSomePhoto(photo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable deleteAllPhoto() {
        return daoPhoto.deleteAllPhoto().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public LiveData<List<LikedPhoto>> getAllLikedPhoto() {
        return allLikedPhoto;
    }
}
