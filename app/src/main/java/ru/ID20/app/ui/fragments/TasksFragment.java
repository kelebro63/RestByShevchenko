package ru.ID20.app.ui.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.content.ContentProvider;

import ru.ID20.app.R;
import ru.ID20.app.adapters.TasksListCursorAdapter;
import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.ItemStatus;
import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.constants.enums.TransactionOperation;
import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.db.models.UserModel;
import ru.ID20.app.listeners.ListItemClickListener;

/**
 * Created  by  s.shevchenko  on  02.07.2015.
 */
public class TasksFragment extends AbstractFragment implements ListItemClickListener {

    public static final String TAG_FG = "TasksFragment";
    public static final boolean IS_LOGGED = true;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private TasksListCursorAdapter adapter;
    private TaskDetailsFragment taskDetailsFragment;

    public static TasksFragment newInstance(FragmentManager fgm) {
        Fragment fg = fgm.findFragmentByTag(TAG_FG);
        if (fg == null) {
            fg = new TasksFragment();
        }
        return (TasksFragment) fg;
    }

    private LoaderManager.LoaderCallbacks<Cursor> tasksListLoaderCallBacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            if (id == Constants.TASKS_LIST_LOADER_ID) {
                String[] projection = new String[]{"DISTINCT " + BaseColumns._ID, TaskModel.TASK_ID, TaskModel.TASK_CREATOR_NAME, TaskModel.TASK_CREATION_DATE, TaskModel.TASK_STATUS, TaskModel.REQUEST_STATUS, TaskModel.REQUEST_ERROR};
                String sortOrder = TaskModel.TASK_ID + " DESC";
                return new CursorLoader(getMainActivity(), ContentProvider.createUri(TaskModel.class, null), projection, null, null, sortOrder);
            }
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
            if (loader.getId() == Constants.TASKS_LIST_LOADER_ID) {
                if (cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex(UserModel.REQUEST_STATUS));
                    RequestStatuses requestStatuses = RequestStatuses.getStatus(status);
                    switch (requestStatuses) {
                        case SENT:
                            progressDialog.show();
                            adapter.swapCursor(null);
                            break;
                        case SUCCESS:
                            progressDialog.hide();
                            if (!isPortraitOrientation) {
                                getHandler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showDetailFragment(cursor.getInt(cursor.getColumnIndex(TaskModel.TASK_ID)));
                                    }
                                });
                            }
                            adapter.swapCursor(cursor);
                            break;
                        case ERROR:
                            progressDialog.hide();
                            Toast.makeText(getActivity(), cursor.getString(cursor.getColumnIndex(UserModel.REQUEST_ERROR)), Toast.LENGTH_SHORT).show();
                            break;
                        case EMPTY:
                            progressDialog.hide();
                            break;
                    }
                } else {
                    getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (taskDetailsFragment != null) {
                                getChildFragmentManager().beginTransaction().remove(taskDetailsFragment).commit();
                                taskDetailsFragment = null;
                            }
                        }
                    });
                    adapter.swapCursor(cursor);
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            adapter.swapCursor(null);
        }
    };

    private void showDetailFragment(int rowID) {
        if (taskDetailsFragment == null) {
            taskDetailsFragment = TaskDetailsFragment.newInstance(getChildFragmentManager(), rowID);
        } else {
            taskDetailsFragment.setNewData(rowID);
        }
        getChildFragmentManager().beginTransaction().replace(R.id.task_details_container, taskDetailsFragment).commit();
    }

    @Override
    protected void initUI(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_tasks);
        layoutManager = new LinearLayoutManager(getMainActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new TasksListCursorAdapter(getMainActivity(), null, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().getSupportLoaderManager().restartLoader(Constants.TASKS_LIST_LOADER_ID, null, tasksListLoaderCallBacks);
    }

    @Override
    protected TabLayout.OnTabSelectedListener initTabSelectedListener(TabLayout.OnTabSelectedListener listener) {
        listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setChangeListOnStatus((String) tab.getTag());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
        tabLayout.setOnTabSelectedListener(listener);
        return listener;
    }

    public void setChangeListOnStatus(String statusName) {
        Loader<Cursor> loader = getMainActivity().getSupportLoaderManager().getLoader(Constants.TASKS_LIST_LOADER_ID);
        if (loader != null) {
            if (statusName != null && !statusName.equals(ItemStatus.STATUS_ALL.getStatusName())) {
                ((CursorLoader) loader).setSelection(TaskModel.TASK_STATUS + " =?");
                ((CursorLoader) loader).setSelectionArgs(new String[]{statusName});
            } else {
                ((CursorLoader) loader).setSelection(null);
                ((CursorLoader) loader).setSelectionArgs(null);
            }
            loader.forceLoad();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getMainActivity().getSupportLoaderManager().destroyLoader(Constants.TASKS_LIST_LOADER_ID);
    }

    @Override
    public String getTagFg() {
        return TAG_FG;
    }

    @Override
    protected boolean isLogged() {
        return IS_LOGGED;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_tasks;
    }

    @Override
    public void showFAB() {

    }

    @Override
    public void setupToolBar(Toolbar toolbar) {
        toolbar.setTitle(getTitle());
    }

    @Override
    public void setupTabLayout(TabLayout tabLayout) {
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setTag(ItemStatus.STATUS_ALL.getStatusName()).setText(R.string.tv_button_all));
        tabLayout.addTab(tabLayout.newTab().setTag(ItemStatus.STATUS_NEW.getStatusName()).setText(R.string.tv_button_new));
        tabLayout.addTab(tabLayout.newTab().setTag(ItemStatus.STATUS_ACCEPTED.getStatusName()).setText(R.string.tv_button_accepted));
        tabLayout.addTab(tabLayout.newTab().setTag(ItemStatus.STATUS_COMPLETED.getStatusName()).setText(R.string.tv_button_completed));
    }

    @Override
    public int getTitle() {
        return R.string.tv_button_tasks;
    }

    @Override
    public boolean checkFields() {
        return false;
    }

    @Override
    public void saveData() {

    }

    @Override
    public void onItemClick(int itemId) {
        if (isPortraitOrientation) {
            getMainActivity().showFragment(TaskDetailsFragment.newInstance(getChildFragmentManager(), itemId), true, false, TransactionOperation.REPLACE, false);
        } else {
            showDetailFragment(itemId);
        }
    }
}
