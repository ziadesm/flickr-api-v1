package echomachine.com.flickrapi_v1.workers;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import echomachine.com.flickrapi_v1.pojo.UserRegister;

import static android.content.Context.MODE_PRIVATE;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_EMAIL;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_EMAIL_PREF;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_NAME;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_NAME_PREF;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_PHONE;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_PHONE_PREF;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_USER_PHONE;

public class SaveRegisterData extends Worker {

    public SaveRegisterData(@NonNull Context context,
                            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String name = getInputData().getString(KEY_SAVE_NAME);
        String email = getInputData().getString(KEY_SAVE_EMAIL);
        String phone = getInputData().getString(KEY_SAVE_PHONE);

        UserRegister user = new UserRegister(name, email, phone);
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(user);

        SharedPreferences preferences = getApplicationContext()
                .getSharedPreferences(KEY_SAVE_USER_PHONE, MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(KEY_SAVE_NAME_PREF, name);
        edit.putString(KEY_SAVE_PHONE_PREF, phone);
        edit.putString(KEY_SAVE_EMAIL_PREF, email);
        edit.apply();

        return Result.success();
    }
}
