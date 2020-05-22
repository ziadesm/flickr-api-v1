package echomachine.com.flickrapi_v1.ui.profile;

import android.com.flickrapi_v1.R;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {
    private EditText emailEt, nameEt, passwordEt, rePasswordEt;
    private Button mResgisterBtn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        emailEt = root.findViewById(R.id.et_email);
        nameEt = root.findViewById(R.id.et_name);
        passwordEt = root.findViewById(R.id.et_password);
        rePasswordEt = root.findViewById(R.id.et_repassword);
        progressBar = root.findViewById(R.id.progress_bar_register_loading);
        mResgisterBtn = root.findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();

        mResgisterBtn.setOnClickListener(v -> newRegisterUser());
        return root;
    }

    private void newRegisterUser() {
        progressBar.setVisibility(View.VISIBLE);
        mResgisterBtn.setEnabled(false);
        String email = emailEt.getText().toString().trim();
        String rePassword = String.valueOf(rePasswordEt.getText());
        String password = String.valueOf(passwordEt.getText());
        String name = nameEt.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEt.setError("E-mail required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Password may wrong or empty");
            return;
        }
        if (TextUtils.isEmpty(rePassword)) {
            rePasswordEt.setError("Password may not match or empty");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            nameEt.setError("Name required");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, new ProfileFragment())
                        .commit();
            } else {
                mResgisterBtn.setEnabled(true);
            }
        });
    }
}