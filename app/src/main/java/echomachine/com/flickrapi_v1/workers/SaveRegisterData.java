package echomachine.com.flickrapi_v1.workers;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import echomachine.com.flickrapi_v1.pojo.UserRegister;

import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_EMAIL;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_NAME;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_PHONE;

public class SaveRegisterData extends Worker {

    public SaveRegisterData(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
        databaseReference.child(email).setValue(user);

        return Result.success();
    }
}
