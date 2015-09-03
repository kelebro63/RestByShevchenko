package ru.ID20.app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import ru.ID20.app.R;
import ru.ID20.app.constants.enums.ItemStatus;
import ru.ID20.app.db.DbUtils;
import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.listeners.ListItemClickListener;
import ru.ID20.app.ui.widgets.CustomFontTextView;

/**
 * Created  by  s.shevchenko  on  02.07.2015.
 */
public class TasksListCursorAdapter extends CursorAdapterRV {

    public static final String TAG = "TasksListCursorAdapter";
    public static final boolean IS_LOGGED = false;
    private ListItemClickListener itemClickListener;

    public TasksListCursorAdapter(Context context, Cursor cursor, ListItemClickListener itemClickListener) {
        super(context, cursor);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public String getTagFg() {
        return TAG;
    }

    @Override
    protected boolean isLogged() {
        return IS_LOGGED;
    }

    @Override
    public boolean onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        final TasksVH hd = (TasksVH) holder;
        ItemStatus taskStatus = ItemStatus.getStatus(cursor.getString(cursor.getColumnIndex(TaskModel.TASK_STATUS)));
        switch (taskStatus){
            case STATUS_NEW:
                hd.rootView.setBackgroundResource(R.drawable.bg_list_item_new);
                break;
            case STATUS_ACCEPTED:
                hd.rootView.setBackgroundResource(R.drawable.bg_list_item_accepted);
                break;
            case STATUS_COMPLETED:
                hd.rootView.setBackgroundResource(R.drawable.bg_list_item_completed);
                break;
        }
        hd.taskId = cursor.getInt(cursor.getColumnIndex(TaskModel.TASK_ID));
        hd.tvTaskCreator.setText((cursor.getString(cursor.getColumnIndex(TaskModel.TASK_CREATOR_NAME))));
        hd.tvTaskItemDate.setText(DbUtils.getDateStringForClaimListItem(cursor.getString(cursor.getColumnIndex(TaskModel.TASK_CREATION_DATE))));
        hd.tvTaskItemNumber.setText(getContext().getResources().getString(R.string.tasks_number, String.valueOf(hd.taskId)));
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_list_item, parent, false);
        return new TasksVH(v);
    }

    public class TasksVH extends RecyclerView.ViewHolder {

        private CustomFontTextView tvTaskItemNumber;
        private CustomFontTextView tvTaskItemDate;
        private CustomFontTextView tvTaskCreator;
        private int taskId;
        private RelativeLayout rootView;

        public TasksVH(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(taskId);
                }
            });
            rootView = (RelativeLayout)itemView.findViewById(R.id.taskItemRootView);
            tvTaskItemNumber = (CustomFontTextView)itemView.findViewById(R.id.tvTaskItemNumber);
            tvTaskItemDate = (CustomFontTextView) itemView.findViewById(R.id.tvTaskItemDate);
            tvTaskCreator = (CustomFontTextView) itemView.findViewById(R.id.tvTaskCreator);
        }
    }
}
