package ru.ID20.app.net;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.RequestType;
import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.db.models.UserModel;
import ru.ID20.app.tools.Logger;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public class RequestManager  {

    public static final String TAG_FG = "RequestManager";
    public static final boolean IS_LOGGED = false;

    protected static final Logger LOG = Logger.getLogger(TAG_FG, IS_LOGGED);

    public static void startLoginRequest(Context context, String login, String pass) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.REQUEST_TYPE_ARG, RequestType.LOGIN_REQUEST);
        args.putString(UserModel.USER_LOGIN, login);
        args.putString(UserModel.USER_PASS, pass);
        startService(context, args);
    }

    public static void startUserDataRequest(Context context) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.REQUEST_TYPE_ARG, RequestType.USER_DATA_REQUEST);
        startService(context, args);
    }

    public static void startTaskListRequest(Context context, boolean isUpdate, int currentPage) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.REQUEST_TYPE_ARG, RequestType.TASK_LIST_REQUEST);
        args.putBoolean(Constants.IS_UPDATE_ARG, isUpdate);
        args.putInt(Constants.CURRENT_PAGE_ARG, currentPage);
        startService(context, args);
    }

    public static void startTaskAcceptRequest(Context context, int taskId){
        Bundle args = new Bundle();
        args.putSerializable(Constants.REQUEST_TYPE_ARG, RequestType.NEW_TASK_ACCEPT);
        args.putInt(TaskModel.TASK_ID, taskId);
        startService(context, args);
    }

    public static void startTaskCompleteRequest(Context context, int taskId) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.REQUEST_TYPE_ARG, RequestType.TASK_COMPLETE);
        args.putInt(TaskModel.TASK_ID, taskId);
        startService(context, args);
    }

    private static void startService(Context context, Bundle args) {
        Intent intent = new Intent(context, IDRequestService.class);
        intent.putExtra(Constants.REQUEST_ARGS, args);
        context.startService(intent);
    }
}
