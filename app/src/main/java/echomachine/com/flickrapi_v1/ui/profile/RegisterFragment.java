package echomachine.com.flickrapi_v1.ui.profile;
import android.com.flickrapi_v1.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private static final String TAG = "Ziad Register";
    private EditText emailEt, nameEt, passwordEt, rePasswordEt;
    private TextView signInTv;
    private Button mRegisterBtn;
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
        mRegisterBtn = root.findViewById(R.id.btn_register);
        signInTv = root.findViewById(R.id.register_words);
        auth = FirebaseAuth.getInstance();

        signInTv.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
        });

        mRegisterBtn.setOnClickListener(v -> newRegisterUser());
        return root;
    }

    private void newRegisterUser() {
        progressBar.setProgress(30);
        progressBar.setMax(100);
        progressBar.setVisibility(View.VISIBLE);
        mRegisterBtn.setEnabled(false);
        String email = emailEt.getText().toString().trim();
        String rePassword = rePasswordEt.getText().toString().trim();
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
        if (TextUtils.isEmpty(rePassword)) {
            rePasswordEt.setError("Password may not match or empty");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            nameEt.setError("Name required");
            return;
        }
        if (!rePassword.equals(password)) {
            passwordEt.setBackgroundColor(Color.RED);
            rePasswordEt.setBackgroundColor(Color.RED);
            return;
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, new ProfileFragment())
                        .commit();
            } else {
                progressBar.setVisibility(View.GONE);
                mRegisterBtn.setEnabled(true);
            }
        });
        mRegisterBtn.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onPrimaryNavigationFragmentChanged(boolean isPrimaryNavigationFragment) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment);
        Log.d(TAG, "onPrimaryNavigationFragmentChanged: ");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }
}