package ru.ID20.app.ui.fragments;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.content.ContentProvider;

import ru.ID20.app.R;
import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.db.models.UserModel;
import ru.ID20.app.net.RequestManager;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public class LoginFragment extends AbstractFragment {

    public static final String TAG_FG = "LoginFragment";
    public static final boolean IS_LOGGED = true;

    public static LoginFragment newInstance() {
        Fragment fg = new LoginFragment();
        return (LoginFragment) fg;
    }

    private LoaderManager.LoaderCallbacks<Cursor> loginCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == Constants.LOGIN_LOADER_ID) {
                String[] projection = new String[]{"DISTINCT " + BaseColumns._ID, UserModel.USER_ID, UserModel.REQUEST_STATUS, UserModel.REQUEST_ERROR};
                return new CursorLoader(getLoginActivity(), ContentProvider.createUri(UserModel.class, null), projection, null, null, null);
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (loader.getId() == Constants.LOGIN_LOADER_ID) {
                if (cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(UserModel.REQUEST_STATUS));
                    RequestStatuses requestStatuses = RequestStatuses.getStatus(status);
                    switch (requestStatuses) {
                        case SENT:
                            progressDialog.show();
                            break;
                        case SUCCESS:
                            RequestManager.startUserDataRequest(getLoginActivity());
                            break;
                        case ERROR:
                            progressDialog.hide();
                            Toast.makeText(getActivity(), cursor.getString(cursor.getColumnIndex(UserModel.REQUEST_ERROR)), Toast.LENGTH_SHORT).show();
                            break;
                        case LOGGED:
                            progressDialog.hide();
                            getLoginActivity().runMainActivity();
                            break;
                    }
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getLoginActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        getLoginActivity().getSupportLoaderManager().restartLoader(Constants.LOGIN_LOADER_ID, null, loginCallback);
    }

    @Override
    protected TabLayout.OnTabSelectedListener initTabSelectedListener(TabLayout.OnTabSelectedListener listener) {
        return null;
    }

    @Override
    public void onStop() {
        super.onStop();
        getLoginActivity().getSupportLoaderManager().destroyLoader(Constants.LOGIN_LOADER_ID);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_login;
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
        return R.string.login_fragment_title;
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
        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestManager.startLoginRequest(getLoginActivity(), "vodmir1", "vodmir1");
            }
        });
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
