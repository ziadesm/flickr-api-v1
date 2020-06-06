package echomachine.com.flickrapi_v1;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import echomachine.com.flickrapi_v1.setting.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "ZiadActivity";
    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView navView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home,
                R.id.navigation_profile,
                R.id.navigation_notifications)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.navigation_login:
                    hideBars();
                    break;
                case R.id.navigation_register:
                    hideBars();
                    break;
                case R.id.navigation_offline:
                    getSupportActionBar().hide();
                    break;
                case R.id.navigation_splash:
                    hideBars();
                    break;
                default:
                    showBars();
            }
        });
    }

    private void hideBars() {
        navView.setVisibility(View.GONE);
        getSupportActionBar().hide();
    }

    private void showBars() {
        navView.setVisibility(View.VISIBLE);
        getSupportActionBar().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}