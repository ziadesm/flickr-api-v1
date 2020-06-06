package echomachine.com.flickrapi_v1.ui.profile;
import echomachine.com.flickrapi_v1.R;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private static final String TAG = "Ziad Register";
    private EditText emailEt, nameEt, passwordEt, phoneEt;
    private TextView signInTv;
    private Button mRegisterBtn;
    private FirebaseAuth auth;
    private NavController navController;

    public RegisterFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        auth = FirebaseAuth.getInstance();

        signInTv.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_login);
        });
        mRegisterBtn.setOnClickListener(v -> newRegisterUser());
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

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                navController.navigate(R.id.navigation_profile);
            } else {
                Log.d(TAG, "newRegisterUser: "+ task.getException().getMessage());
                mRegisterBtn.setEnabled(true);
            }
        });
    }
}