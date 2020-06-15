package echomachine.com.flickrapi_v1.ui.selected;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import echomachine.com.flickrapi_v1.interfaces.PhotoClient;
import echomachine.com.flickrapi_v1.pojo.Photo;
import echomachine.com.flickrapi_v1.pojo.SelectedPhotos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectedViewModel extends ViewModel {
    private static final String TAG = "SelectedViewModel";
    private Uri imageUri;
    private String user_id;
    MutableLiveData<List<Photo>> mutableLiveData = new MutableLiveData<>();

    public SelectedViewModel() {
    }

    public void getProfilePhotosPage(String user_id) {
        Log.d(TAG, "I'M HERE BY THE WAY");
        Log.d(TAG, "method" + user_id);
        PhotoClient.getINSTANCE().getProfilePhotosPage(user_id)
                .enqueue(new Callback<SelectedPhotos>() {
                    @Override
                    public void onResponse(Call<SelectedPhotos> call, Response<SelectedPhotos> response) {
                        Log.d(TAG, "onResponse: "+ response.body().getStat());
                        mutableLiveData.setValue(response.body().getPhotos().getPhoto());
                    }

                    @Override
                    public void onFailure(Call<SelectedPhotos> call, Throwable t) {

                    }
                });
    }

    public MutableLiveData<List<Photo>> getMutableLiveData() {
        return mutableLiveData;
    }

    private Uri uriOrNull(String uriString) {
        if (!TextUtils.isEmpty(uriString)) {
            return Uri.parse(uriString);
        }
        return null;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = uriOrNull(imageUri);
    }

    public void setUserId(String photoOwner) {
        this.user_id = photoOwner;
        Log.d(TAG, "" + user_id);
    }

    public String getUserId() {
        return user_id;
    }
}
