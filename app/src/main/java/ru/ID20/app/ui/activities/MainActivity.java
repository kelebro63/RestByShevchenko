package ru.ID20.app.ui.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;

import com.activeandroid.content.ContentProvider;

import ru.ID20.app.R;
import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.ItemStatus;
import ru.ID20.app.constants.enums.TransactionOperation;
import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.db.models.UserModel;
import ru.ID20.app.net.RequestManager;
import ru.ID20.app.ui.fragments.TasksFragment;
import ru.ID20.app.ui.widgets.CustomFontTextView;
import ru.ID20.app.ui.widgets.MyActionView;

public class MainActivity extends AbstractBaseActionBarActivity {

    private MenuItem toolBarClaimMenuItem, toolBarTaskMenuItem;
    private int newTasksItemCount;
    private int newClaimsItemCount;

    private LoaderManager.LoaderCallbacks<Cursor> newItemLoaderCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCallBackLoader();
        if (savedInstanceState == null){
            runStartRequests();
        }
    }

    private void initCallBackLoader(){
        newItemLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                if (id == Constants.NEW_TASKS_COUNT_LOADER_ID) {
                    String[] projection = new String[]{"DISTINCT " + BaseColumns._ID, TaskModel.TASK_STATUS};
                    String selection = TaskModel.TASK_STATUS + " =?";
                    String[] selectionArgs = new String[]{ItemStatus.STATUS_NEW.getStatusName()};
                    return new CursorLoader(MainActivity.this, ContentProvider.createUri(TaskModel.class, null), projection, selection, selectionArgs, null);
                }
                return null;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                if (loader.getId() == Constants.NEW_TASKS_COUNT_LOADER_ID) {
                    newTasksItemCount = cursor.getCount();
                    if (newTasksItemCount > 0 && toolBarTaskMenuItem != null) {
                        showNewItemCount(toolBarTaskMenuItem, newTasksItemCount);
                    } else {
                        hideMenuItem(toolBarTaskMenuItem);
                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        toolBarClaimMenuItem = menu.findItem(R.id.ab_claims_item);
        toolBarTaskMenuItem = menu.findItem(R.id.ab_tasks_item);
        ((MyActionView) toolBarClaimMenuItem.getActionView()).setImage(getResources().getDrawable(R.mipmap.menu_claims_logo));
        ((MyActionView) toolBarTaskMenuItem.getActionView()).setImage(getResources().getDrawable(R.mipmap.menu_tasks_logo));

        if (newTasksItemCount > 0) {
            showNewItemCount(toolBarTaskMenuItem, newTasksItemCount);
        } else {
            hideMenuItem(toolBarTaskMenuItem);
        }

        if (newClaimsItemCount > 0) {
            showNewItemCount(toolBarClaimMenuItem, newClaimsItemCount);
        } else {
            hideMenuItem(toolBarClaimMenuItem);
        }
        return true;
    }

    private void showNewItemCount(MenuItem item, int count){
        if (item != null){
            ((MyActionView) item.getActionView()).showNotification(count);
            item.setVisible(true);
        }
    }

    private void hideMenuItem(MenuItem item){
        if (item != null){
            item.setVisible(false);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        showFragment(TasksFragment.newInstance(getSupportFragmentManager()), true, true, TransactionOperation.REPLACE, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(Constants.NEW_TASKS_COUNT_LOADER_ID, null, newItemLoaderCallback);
    }

    @Override
    protected void onDestroy() {
        getSupportLoaderManager().destroyLoader(Constants.NEW_TASKS_COUNT_LOADER_ID);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    void initUI() {

    }

    @Override
    int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getDrawerLayoutId() {
        return R.id.drawer_layout;
    }

    @Override
    int getActionBarLayoutId() {
        return R.id.main_activity_toolbar;
    }

    @Override
    void initNavigationItemSelectListener() {
        final CustomFontTextView tvHeader = (CustomFontTextView)navigationView.findViewById(R.id.tv_header_user_name);
        getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                UserModel user = UserModel.getLoggedUser();
                final StringBuilder nameBuilder = new StringBuilder();
                nameBuilder.append(user.getUserName()).append(" ").append(user.getUserPatronimic()).append(" ").append(user.getUserSurename());
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        tvHeader.setText(nameBuilder.toString());
                    }
                });
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.claims_item:
                        break;
                    case R.id.waybills_item:
                        break;
                    case R.id.tasks_item:
                        showFragment(TasksFragment.newInstance(getSupportFragmentManager()), true, true, TransactionOperation.REPLACE, false);
                        break;
                    case R.id.settings_item:
                        break;
                }
                getDrawerLayout().closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    private void runStartRequests() {
        RequestManager.startTaskListRequest(this, false, Constants.START_PAGE_NUMBER);
    }
}
