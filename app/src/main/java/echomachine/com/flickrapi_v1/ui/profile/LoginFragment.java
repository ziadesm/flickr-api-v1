package echomachine.com.flickrapi_v1.ui.profile;

import android.com.flickrapi_v1.R;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private static final String TAG = "Ziad Login";
    private EditText emailEt, passwordEt;
    private Button loginBtn;
    private TextView signUpTv;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        emailEt = root.findViewById(R.id.et_email_login);
        passwordEt = root.findViewById(R.id.et_password_login);
        loginBtn = root.findViewById(R.id.btn_login);
        signUpTv = root.findViewById(R.id.register_words);
        progressBar = root.findViewById(R.id.progress_bar_login_loading);
        auth = FirebaseAuth.getInstance();

        signUpTv.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.nav_host_fragment, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });

        loginBtn.setOnClickListener(c -> loginToFirebase());
        return root;
    }

    private void loginToFirebase() {
        progressBar.setProgress(30);
        progressBar.setMax(100);
        loginBtn.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        String email = emailEt.getText().toString();
        String password = passwordEt.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailEt.setError("E-mail required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEt.setError("Password may wrong or empty");
            return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.INVISIBLE);
            if (task.isSuccessful() && task.isComplete()) {
                FragmentManager fm = getParentFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.nav_host_fragment, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                loginBtn.setEnabled(true);
            }
        });

    }
}
