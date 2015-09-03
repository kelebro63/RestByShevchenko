package ru.ID20.app.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import ru.ID20.app.R;
import ru.ID20.app.ui.widgets.MyDrawerToggle;

/**
 * Created  by  s.shevchenko  on  29.06.2015.
 */
public abstract class AbstractBaseActionBarActivity extends AbstractBaseActivity {

    protected Toolbar toolbar;
    protected TabLayout tabLayout;
    protected MyDrawerToggle drawerToggle;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        tabLayout = (TabLayout)findViewById(R.id.tab_layout_tasks);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        initNavigationItemSelectListener();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected abstract int getDrawerLayoutId();

    abstract int getActionBarLayoutId();

    abstract void initNavigationItemSelectListener();

    protected void initActionBar(){
        toolbar = (Toolbar)findViewById(getActionBarLayoutId());
        drawerLayout = (DrawerLayout) findViewById(getDrawerLayoutId());
        drawerToggle = new MyDrawerToggle(this, drawerLayout, toolbar, R.string.menu, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        setSupportActionBar(toolbar);


    }


    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
