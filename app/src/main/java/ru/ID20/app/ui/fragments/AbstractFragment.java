package ru.ID20.app.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.ID20.app.tools.Logger;
import ru.ID20.app.ui.activities.AbstractBaseActionBarActivity;
import ru.ID20.app.ui.activities.AbstractBaseActivity;
import ru.ID20.app.ui.activities.LoginActivity;
import ru.ID20.app.ui.activities.MainActivity;
import ru.ID20.app.ui.widgets.MaterialDialog;
import ru.ID20.app.ui.widgets.MaterialProgressBar;

/**
 * Created by s.shevchenko on 06.02.2015.
 */
public abstract class AbstractFragment extends Fragment {

    private AbstractBaseActivity mActivity;
    protected MaterialProgressBar progressDialog;
    protected MaterialDialog dialog;
    protected Toolbar toolbar;
    protected TabLayout tabLayout;
    protected TabLayout.OnTabSelectedListener tabSelectedListener;
    protected boolean isPortraitOrientation;

    protected final Logger LOG = Logger.getLogger(getTagFg(), isLogged());

    public abstract String getTagFg();

    protected abstract boolean isLogged();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (AbstractBaseActivity) activity;
        if (mActivity instanceof AbstractBaseActionBarActivity){
            toolbar = ((AbstractBaseActionBarActivity)mActivity).getToolbar();
            tabLayout = ((AbstractBaseActionBarActivity) mActivity).getTabLayout();

        }
        progressDialog = new MaterialProgressBar(activity);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        dialog = new MaterialDialog();
    }

    @Override
    public void onDetach() {
        this.mActivity = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPortraitOrientation = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutID(), container, false);
        initUI(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (toolbar != null){
            setupToolBar(toolbar);
            setupTabLayout(tabLayout);
        }
        initTabSelectedListener(tabSelectedListener);
    }

    @Override
    public void onDestroyView() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getDialog() != null && getDialog().isVisible()) {
            getDialog().dismiss();
        }
    }

    protected void showAlertDialog(String message){
        MaterialDialog.Builder builder = MaterialDialog.newBuilder(dialog);
        builder.setMessageText(message).setPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        }).build();
        dialog.show(getFragmentManager(), "AlertDialog");
    }

    protected android.os.Handler getHandler() {
        return this.mActivity.getHandler();
    }

    public Context getApplicationContext() {
        return this.mActivity.getApplicationContext();
    }

    public MainActivity getMainActivity() {
        return (MainActivity) mActivity;
    }

    public LoginActivity getLoginActivity() {
        return (LoginActivity) mActivity;
    }

    public MaterialDialog getDialog() {
        return dialog;
    }

    protected abstract TabLayout.OnTabSelectedListener initTabSelectedListener(TabLayout.OnTabSelectedListener listener);

    /**
     * @return integer for resource fragment layout R.layout.some_layout
     */
    protected abstract int getLayoutID();

    public abstract void showFAB();

    /**
     * In this method initialize Toolbar
     * @param toolbar Toolbar instance
     */
    public abstract void setupToolBar(Toolbar toolbar);

    /**
     * In this method initialize TabLayout
     *
     * @param tabLayout TabLayout instance
     */
    public abstract void setupTabLayout(TabLayout tabLayout);

    /**
     * Return title string resource id for a fragment
     *
     * @return R.string.some_string_item
     */
    public abstract int getTitle();

    /**
     * Must return true to get an action for action bar buttons
     *
     * @return true - if everything is good and action should be done, false - if something wrong and action should not be done
     */
    public abstract boolean checkFields();

    /**
     * Must be called after {@link AbstractFragment#checkFields()} if it will return true.
     * Make some action here to write data into DB
     */
    public abstract void saveData();

    /**
     * Get instances for views in fragment layout
     *
     * @param view root view of the fragment
     */
    protected abstract void initUI(View view);
}
