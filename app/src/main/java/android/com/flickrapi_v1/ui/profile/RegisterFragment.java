package android.com.flickrapi_v1.ui.profile;

import android.com.flickrapi_v1.R;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class RegisterFragment extends Fragment {
    @BindViews({R.id.et_email, R.id.et_name, R.id.et_password, R.id.et_repassword})
    EditText emailEt, nameEt, passwordEt, rePasswordEt;

    @BindView(R.id.btn_register)
    Button mResgisterBtn;

    @BindView(R.id.progress_bar_register_loading)
    ProgressBar progressBar;
    FirebaseAuth auth;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, root);
        auth = FirebaseAuth.getInstance();

        rePasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String password = passwordEt.getText().toString();
                if (rePasswordEt.getText().toString().equals(password)) {
                    passwordEt.setBackgroundColor(Color.GREEN);
                    rePasswordEt.setBackgroundColor(Color.GREEN);
                }
                rePasswordEt.setBackgroundColor(Color.RED);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String password = passwordEt.getText().toString();
                if (rePasswordEt.getText().toString().equals(password)) {
                    passwordEt.setBackgroundColor(Color.GREEN);
                    rePasswordEt.setBackgroundColor(Color.GREEN);
                } else {
                    passwordEt.setBackgroundColor(Color.RED);
                    rePasswordEt.setBackgroundColor(Color.RED);
                }
            }
        });

        mResgisterBtn.setOnClickListener(v -> newRegisterUser());
        return root;
    }

    private void newRegisterUser() {
        String email = emailEt.getText().toString().trim();
        String password = passwordEt.getText().toString();
        String rePassword = rePasswordEt.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailEt.setError("E-mail required");
        } else if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Password may wrong or empty");
        } else if (TextUtils.isEmpty(rePassword)) {
            rePasswordEt.setError("Password may not match or empty");
        }
    }
}