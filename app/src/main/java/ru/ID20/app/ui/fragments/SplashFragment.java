package ru.ID20.app.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ru.ID20.app.R;
import ru.ID20.app.constants.enums.TransactionOperation;
import ru.ID20.app.db.DbUtils;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public class SplashFragment extends AbstractFragment {

    public static final String TAG_FG = "SplashFragment";
    public static final boolean IS_LOGGED = false;

    public static SplashFragment newInstance() {
        Fragment fg = new SplashFragment();
        return (SplashFragment) fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getLoginActivity().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                DbUtils.clearDB();
                getLoginActivity().getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        if (getLoginActivity() != null){
                            getLoginActivity().showFragment(LoginFragment.newInstance(), false, true, TransactionOperation.REPLACE, false);
                        }
                    }
                });
            }
        });
    }

    @Override
    protected TabLayout.OnTabSelectedListener initTabSelectedListener(TabLayout.OnTabSelectedListener listener) {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_splash;
    }

    @Override
    public void showFAB() {

    }

    @Override
    public void setupToolBar(Toolbar toolbar) {

    }

    @Override
    public void setupTabLayout(TabLayout tabLayout) {

    }

    @Override
    public int getTitle() {
        return 0;
    }

    @Override
    public boolean checkFields() {
        return false;
    }

    @Override
    public void saveData() {

    }

    @Override
    protected void initUI(View view) {

    }

    @Override
    public String getTagFg() {
        return TAG_FG;
    }

    @Override
    protected boolean isLogged() {
        return IS_LOGGED;
    }

}
