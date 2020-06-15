package echomachine.com.flickrapi_v1.ui.profile;

import echomachine.com.flickrapi_v1.R;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private static final String TAG = "Ziad Login";
    private EditText emailEt, passwordEt;
    private Button loginBtn;
    private TextView signUpTv;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private NavController navController;

    public LoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.navigate(R.id.navigation_register);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
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
            Fragment registerFragment = new RegisterFragment();
            FragmentTransaction fm = getParentFragmentManager().beginTransaction();
            fm.replace(R.id.nav_host_fragment, registerFragment);
            fm.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            fm.remove(this);
            fm.commit();
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
