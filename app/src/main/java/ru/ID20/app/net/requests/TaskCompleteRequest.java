package ru.ID20.app.net.requests;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import ru.ID20.app.constants.enums.ItemStatus;
import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.db.RequestParser;
import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.db.models.UserModel;

/**
 * Created  by  s.shevchenko  on  08.07.2015.
 */
public class TaskCompleteRequest extends BaseRequest{

    public TaskCompleteRequest(Context context, Intent intent) {
        super(context, intent);
    }

    @Override
    String getLogTag() {
        return "TaskCompleteRequest";
    }

    @Override
    public void run() {
        TaskModel task = TaskModel.getTaskWithId(getArg().getInt(TaskModel.TASK_ID));
        task.setRequestStatus(RequestStatuses.SENT.getStatus());
        task.save();
        UserModel userModel = UserModel.getLoggedUser();

        addNewValuePair(UserModel.SESSION_TOKEN, userModel.getSessionToken());
        addNewValuePair(TaskModel.TASK_ID, String.valueOf(getArg().getInt(TaskModel.TASK_ID)));

        setData();
        try {
            setResponseData(RequestParser.parseSimpleRequest(getResponseString()));
        } catch (Exception e) {
            task.setRequestStatus(RequestStatuses.ERROR.getStatus());
            task.setRequestError(getResponseData().getErrorString());
            task.save();
            return;
        }

        if (getResponseData().isSuccess()) {
            JSONObject jsonObject = getResponseData().getResponseJson();
            task.setTaskStatus(ItemStatus.STATUS_COMPLETED.getStatusName());
            task.setTaskUpdatedDate(jsonObject.optString("updated"));
            task.setRequestStatus(RequestStatuses.SUCCESS.getStatus());
        } else {
            task.setRequestStatus(RequestStatuses.ERROR.getStatus());
            task.setRequestError(getResponseData().getErrorString());
        }

        task.save();
    }
}
