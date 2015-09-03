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
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import com.activeandroid.content.ContentProvider;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import ru.ID20.app.R;
import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.ItemStatus;
import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.db.DbUtils;
import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.net.RequestManager;
import ru.ID20.app.ui.widgets.CustomFontTextView;
import ru.ID20.app.ui.widgets.MaterialDialog;

/**
 * Created  by  s.shevchenko  on  02.07.2015.
 */
public class TaskDetailsFragment extends AbstractFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG_FG = "TaskDetailsFragment";
    public static final boolean IS_LOGGED = true;

    private CustomFontTextView tvTaskNumber, tvTaskSender, tvTaskText, tvTaskStatus, tvDateCreation, tvDateCompleted;
    private FloatingActionButton btnAction;
    private int taskId;
    private ItemStatus currentStatus;

    public static TaskDetailsFragment newInstance(FragmentManager fgm, int taskId) {
        Fragment fg = fgm.findFragmentByTag(TAG_FG);
        if (fg == null) {
            fg = new TaskDetailsFragment();
        }
        Bundle args = new Bundle();
        args.putInt(TaskModel.TASK_ID, taskId);
        fg.setArguments(args);
        return (TaskDetailsFragment) fg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskId = getArguments().getInt(TaskModel.TASK_ID, -1);
    }

    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().getSupportLoaderManager().restartLoader(Constants.TASKS_DETAILS_LOADER_ID, null, this);
    }

    @Override
    protected TabLayout.OnTabSelectedListener initTabSelectedListener(TabLayout.OnTabSelectedListener listener) {
        return null;
    }

    @Override
    public void onStop() {
        super.onStop();
        getMainActivity().getSupportLoaderManager().destroyLoader(Constants.TASKS_DETAILS_LOADER_ID);
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
        return R.layout.fragment_task_details;
    }

    @Override
    public void setupToolBar(Toolbar toolbar) {
    }

    @Override
    public void setupTabLayout(TabLayout tabLayout) {
        if (isPortraitOrientation) {
            tabLayout.setVisibility(View.GONE);
        }
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
        tvTaskNumber = (CustomFontTextView) view.findViewById(R.id.tvTaskNumber);
        tvTaskSender = (CustomFontTextView) view.findViewById(R.id.tvTaskSender);
        tvTaskText = (CustomFontTextView) view.findViewById(R.id.tvTaskText);
        tvTaskStatus = (CustomFontTextView) view.findViewById(R.id.tvTaskStatus);
        tvDateCreation = (CustomFontTextView) view.findViewById(R.id.tvTaskDateCreation);
        tvDateCompleted = (CustomFontTextView) view.findViewById(R.id.tvTaskDateComplete);
        btnAction = (FloatingActionButton) view.findViewById(R.id.btnTaskAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        ObservableScrollView scrollView = (ObservableScrollView)view.findViewById(R.id.scroll_view_task_details);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!btnAction.isVisible() && getCurrentStatus() != ItemStatus.STATUS_COMPLETED) {
                        btnAction.show();
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (btnAction.isVisible() && getCurrentStatus() != ItemStatus.STATUS_COMPLETED) {
                        btnAction.hide();
                    }
                }
                return false;
            }
        });

    }

    public void setNewData(int newTaskId) {
        taskId = newTaskId;
        Loader<Cursor> loader = null;
        if (getMainActivity() != null){
            loader = getMainActivity().getSupportLoaderManager().getLoader(Constants.TASKS_DETAILS_LOADER_ID);
        }
        if (loader != null){
            if (taskId > 0) {
                ((CursorLoader) loader).setSelection(TaskModel.TASK_ID + " =?");
                ((CursorLoader) loader).setSelectionArgs(new String[]{String.valueOf(taskId)});
            } else {
                ((CursorLoader) loader).setSelection(null);
                ((CursorLoader) loader).setSelectionArgs(null);
            }
            loader.forceLoad();
        }

    }

    private void showDialog() {
        MaterialDialog.Builder builder = MaterialDialog.newBuilder(dialog);
        switch (getCurrentStatus()){
            case STATUS_NEW:
                builder.setMessageText(getString(R.string.dialog_question_accept_task));
                builder.setPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        RequestManager.startTaskAcceptRequest(getMainActivity(), taskId);
                    }
                });
                break;
            case STATUS_ACCEPTED:
                builder.setMessageText(getString(R.string.dialog_question_complete_task));
                builder.setPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        RequestManager.startTaskCompleteRequest(getMainActivity(), taskId);
                    }
                });
                break;
        }
                builder.setNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                })
                .build();
        dialog.show(getMainActivity().getSupportFragmentManager(), "MaterialDialog");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == Constants.TASKS_DETAILS_LOADER_ID) {
            String[] projection = new String[]{"DISTINCT " + BaseColumns._ID,
                    TaskModel.TASK_ID,
                    TaskModel.TASK_CREATOR_NAME,
                    TaskModel.TASK_CREATION_DATE,
                    TaskModel.TASK_TEXT,
                    TaskModel.TASK_COMPLETED_DATE,
                    TaskModel.TASK_UPDATED_DATE,
                    TaskModel.TASK_STATUS,
                    TaskModel.REQUEST_STATUS,
                    TaskModel.REQUEST_ERROR};
            String sortOrder = TaskModel.TASK_UPDATED_DATE + " DESC";
            String selection = TaskModel.TASK_ID + " =?";
            String[] selectionArgs = new String[]{String.valueOf(taskId)};
            return new CursorLoader(getMainActivity(), ContentProvider.createUri(TaskModel.class, null), projection, selection, selectionArgs, sortOrder);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
        if (loader.getId() == Constants.TASKS_DETAILS_LOADER_ID) {
            if (cursor.moveToFirst()) {
                RequestStatuses requestStatus = RequestStatuses.getStatus(cursor.getInt(cursor.getColumnIndex(TaskModel.REQUEST_STATUS)));
                switch (requestStatus){
                    case SENT:
                        progressDialog.show();
                        break;
                    case SUCCESS:
                        tvTaskNumber.setText(getString(R.string.tasks_number, String.valueOf(cursor.getInt(cursor.getColumnIndex(TaskModel.TASK_ID)))));
                        tvTaskSender.setText(cursor.getString(cursor.getColumnIndex(TaskModel.TASK_CREATOR_NAME)));
                        tvTaskText.setText(cursor.getString(cursor.getColumnIndex(TaskModel.TASK_TEXT)));
                        ItemStatus status = ItemStatus.getStatus(cursor.getString(cursor.getColumnIndex(TaskModel.TASK_STATUS)));
                        checkTaskStatus(status);
                        tvTaskStatus.setText(getString(status.getStringResId()));
                        tvDateCreation.setText(DbUtils.getDateAndTime(cursor.getString(cursor.getColumnIndex(TaskModel.TASK_CREATION_DATE))));
                        if (status == ItemStatus.STATUS_COMPLETED) {
                            tvDateCompleted.setText(DbUtils.getDateAndTime(cursor.getString(cursor.getColumnIndex(TaskModel.TASK_COMPLETED_DATE))));
                        } else {
                            tvDateCompleted.setText("");
                        }
                        progressDialog.dismiss();
                        break;
                    case ERROR:
                        progressDialog.dismiss();
                        final String message = cursor.getString(cursor.getColumnIndex(TaskModel.REQUEST_ERROR));
                        getMainActivity().getExecutorService().execute(new Runnable() {
                            @Override
                            public void run() {
                                TaskModel taskModel = TaskModel.getTaskWithId(taskId);
                                taskModel.setRequestStatus(RequestStatuses.SUCCESS.getStatus());
                                taskModel.save();
                            }
                        });
                        getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                showAlertDialog(message);
                            }
                        });
                        break;
                    default:
                        progressDialog.dismiss();
                        break;
                }

            }
        }
    }

    private void checkTaskStatus(ItemStatus status) {
        switch (status) {
            case STATUS_NEW:
                if (getCurrentStatus() != status) {
                    btnAction.hide();
                }
                btnAction.setColorNormalResId(R.color.status_new_red);
                btnAction.setColorPressedResId(R.color.status_new_red_pressed);
                showFAB();
                break;
            case STATUS_ACCEPTED:
                if (getCurrentStatus() != status) {
                    btnAction.hide();
                }
                btnAction.setColorNormalResId(R.color.status_accepted_blue);
                btnAction.setColorPressedResId(R.color.status_accepted_blue_pressed);
                showFAB();
                break;
            case STATUS_COMPLETED:
                btnAction.hide();
                    btnAction.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (!btnAction.isVisible()){
                                btnAction.setVisibility(View.GONE);
                            }
                        }
                    }, 200);
                break;
            default:
                btnAction.setVisibility(View.GONE);
                btnAction.hide();
                break;
        }
        setCurrentStatus(status);
    }

    @Override
    public void showFAB() {
        if (!btnAction.isVisible() || btnAction.getVisibility() != View.VISIBLE) {
            btnAction.setVisibility(View.VISIBLE);
            btnAction.show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public ItemStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ItemStatus currentStatus) {
        this.currentStatus = currentStatus;
    }
}
