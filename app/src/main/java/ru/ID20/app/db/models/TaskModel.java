package ru.ID20.app.db.models;

import android.provider.BaseColumns;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import ru.ID20.app.tools.Tools;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
@Table(name = "Tasks", id = BaseColumns._ID)
public class TaskModel extends BaseModel {

    public static final String TASK_ID = "id";
    @Column(name = TASK_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName(TASK_ID)
    private Integer taskId;

    public static final String TASK_CREATOR_NAME = "TASK_CREATOR_NAME";
    @Column(name = TASK_CREATOR_NAME)
    @SerializedName("from")
    private String taskCreatorName;

    public static final String TASK_TEXT = "text";
    @Column(name = TASK_TEXT)
    @SerializedName(TASK_TEXT)
    private String taskText;

    public static final String TASK_STATUS = "status";
    @Column(name = TASK_STATUS)
    @SerializedName(TASK_STATUS)
    private String taskStatus;

    public static final String TASK_CREATION_DATE = "created";
    @Column(name = TASK_CREATION_DATE)
    @SerializedName(TASK_CREATION_DATE)
    private String taskCreationDate;

    public static final String TASK_COMPLETED_DATE = "done";
    @Column(name = TASK_COMPLETED_DATE)
    @SerializedName(TASK_COMPLETED_DATE)
    private String taskEndedDate;

    public static final String TASK_UPDATED_DATE = "updated";
    @Column(name = TASK_UPDATED_DATE)
    @SerializedName(TASK_UPDATED_DATE)
    private String taskUpdatedDate;

    public static void deleteAll() {
        Tools.ensureNotOnMainThread();
        new Delete().from(TaskModel.class).execute();
    }

    public static TaskModel getTaskWithId(int taskId){
        return new Select().from(TaskModel.class).where(TASK_ID + " =?", taskId).executeSingle();
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskCreatorName() {
        return taskCreatorName;
    }

    public void setTaskCreatorName(String taskCreatorName) {
        this.taskCreatorName = taskCreatorName;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskCreationDate() {
        return taskCreationDate;
    }

    public void setTaskCreationDate(String taskCreationDate) {
        this.taskCreationDate = taskCreationDate;
    }

    public String getTaskEndedDate() {
        return taskEndedDate;
    }

    public void setTaskEndedDate(String taskEndedDate) {
        this.taskEndedDate = taskEndedDate;
    }

    public String getTaskUpdatedDate() {
        return taskUpdatedDate;
    }

    public void setTaskUpdatedDate(String taskUpdatedDate) {
        this.taskUpdatedDate = taskUpdatedDate;
    }
}
