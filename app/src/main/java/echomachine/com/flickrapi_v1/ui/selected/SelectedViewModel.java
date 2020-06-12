package echomachine.com.flickrapi_v1.ui.selected;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import echomachine.com.flickrapi_v1.interfaces.PhotoClient;
import echomachine.com.flickrapi_v1.pojo._PhotoModel;
import retrofit2.Call;

public class SelectedViewModel extends ViewModel {
    private MutableLiveData<List<_PhotoModel.Photos.Photo>> mutableLiveData;
    public SelectedViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public Call<_PhotoModel> getProfilePhotosPage(String user_id) {
        return PhotoClient.getINSTANCE().getProfilePhotosPage(user_id);
    }

    public MutableLiveData<List<_PhotoModel.Photos.Photo>> getMutableLiveData() {
        return mutableLiveData;
    }
}
