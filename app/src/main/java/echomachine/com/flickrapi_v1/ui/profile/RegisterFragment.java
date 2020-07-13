package echomachine.com.flickrapi_v1.ui.profile;
import echomachine.com.flickrapi_v1.R;
import echomachine.com.flickrapi_v1.workers.SaveRegisterData;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_EMAIL;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_NAME;
import static echomachine.com.flickrapi_v1.Constant.KEY_SAVE_PHONE;

public class RegisterFragment extends Fragment {

    private static final String TAG = "Ziad Register";
    private EditText emailEt, nameEt, passwordEt, phoneEt;
    private TextView signInTv;
    private Button mRegisterBtn;
    private FirebaseAuth auth;
    private NavController navController;
    private ProgressBar progressBar;
    private Handler handler;
    private WorkManager manager;

    public RegisterFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = WorkManager.getInstance(getContext());
        handler = new Handler();
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.navigate(R.id.navigation_home);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        emailEt = root.findViewById(R.id.et_email);
        nameEt = root.findViewById(R.id.et_name);
        passwordEt = root.findViewById(R.id.et_password);
        phoneEt = root.findViewById(R.id.et_phone_number);
        mRegisterBtn = root.findViewById(R.id.btn_register);
        signInTv = root.findViewById(R.id.login_words);
        progressBar = root.findViewById(R.id.register_progress_bar);
        auth = FirebaseAuth.getInstance();

        signInTv.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_login);
        });
        mRegisterBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRegisterBtn.setEnabled(false);
                    progressBar.setProgress(60);
                }
            }, 700);
            newRegisterUser();
        });
        return root;
    }

    private void newRegisterUser() {

        String email = emailEt.getText().toString().trim();
        String phone = phoneEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String name = nameEt.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEt.setError("E-mail required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Password may wrong or empty");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            phoneEt.setError("Password may not match or empty");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            nameEt.setError("Name required");
            return;
        }

        handler.postDelayed(() -> auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressBar.setProgress(80);
                applySavingDataToFire(name, email, phone);
                navController.navigate(R.id.navigation_profile);
            } else {
                Log.d(TAG, "newRegisterUser: "+ task.getException().getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                mRegisterBtn.setEnabled(true);
            }
        }), 1000);
    }

    private Data createUserData(String name, String email, String phone) {
        Data.Builder builder = new Data.Builder();
        builder.putString(KEY_SAVE_NAME, name);
        builder.putString(KEY_SAVE_EMAIL, email);
        builder.putString(KEY_SAVE_PHONE, phone);
        return builder.build();
    }

    public void applySavingDataToFire(String name, String email, String phone) {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(SaveRegisterData.class)
                .setInputData(createUserData(name, email, phone))
                .build();

        manager.enqueue(request);
    }
}