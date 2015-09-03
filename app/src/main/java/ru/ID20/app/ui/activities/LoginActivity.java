package ru.ID20.app.ui.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import ru.ID20.app.R;
import ru.ID20.app.constants.enums.TransactionOperation;
import ru.ID20.app.ui.fragments.LoginFragment;
import ru.ID20.app.ui.fragments.SplashFragment;

/**
 * Created  by  s.shevchenko  on  29.06.2015.
 */
public class LoginActivity extends AbstractBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        if (savedInstanceState == null){
            showFragment(SplashFragment.newInstance(), false, true, TransactionOperation.REPLACE, false);
        } else {
            showFragment(LoginFragment.newInstance(), false, true, TransactionOperation.REPLACE, false);
        }
    }



    public void runMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    void initUI() {
    }

    @Override
    int getLayoutId() {
        return R.layout.activity_login;
    }
}
