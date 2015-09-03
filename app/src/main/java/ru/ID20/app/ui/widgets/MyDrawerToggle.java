package ru.ID20.app.ui.widgets;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ru.ID20.app.ui.activities.AbstractBaseActionBarActivity;
import ru.ID20.app.ui.fragments.AbstractFragment;

/**
 * Created  by  s.shevchenko  on  10.07.2015.
 */
public class MyDrawerToggle extends ActionBarDrawerToggle {

    private AbstractBaseActionBarActivity activity;

    public MyDrawerToggle(AbstractBaseActionBarActivity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = activity;
    }

    public MyDrawerToggle(AbstractBaseActionBarActivity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = activity;
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        FragmentManager.BackStackEntry entry = activity.getSupportFragmentManager().getBackStackEntryAt(activity.getSupportFragmentManager().getBackStackEntryCount() - 1);
        AbstractFragment fragment = ((AbstractFragment) activity.getSupportFragmentManager().findFragmentByTag(entry.getName()));
        fragment.showFAB();
    }
}
