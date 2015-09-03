package ru.ID20.app.net;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.RequestType;
import ru.ID20.app.net.requests.LoginRequest;
import ru.ID20.app.net.requests.TaskAcceptRequest;
import ru.ID20.app.net.requests.TaskCompleteRequest;
import ru.ID20.app.net.requests.TaskListRequest;
import ru.ID20.app.net.requests.UserDataRequest;
import ru.ID20.app.tools.Logger;
import ru.ID20.app.tools.Tools;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public class IDRequestService extends Service {

    public static final String TAG = "IDRequestService";
    public static final boolean IS_LOGGED = true;

    private final Logger LOG = Logger.getLogger(TAG, isLogged());

    private static ScheduledExecutorService executorService;

    public static void stop(Context context) {
        context.stopService(new Intent(context, IDRequestService.class));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newScheduledThreadPool(3);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return START_NOT_STICKY;
        }

        final Bundle args = intent.getBundleExtra(Constants.REQUEST_ARGS);
        RequestType requestType = (RequestType) args.getSerializable(Constants.REQUEST_TYPE_ARG);

        switch (requestType){
            case LOGIN_REQUEST:
                executorService.execute(LoginRequest.getNewInstance(this,intent));
                break;
            case USER_DATA_REQUEST:
                executorService.execute(UserDataRequest.getNewInstance(this,intent));
                break;
            case TASK_LIST_REQUEST:
                executorService.execute(new TaskListRequest(this, intent));
                break;
            case NEW_TASK_ACCEPT:
                executorService.execute(new TaskAcceptRequest(this, intent));
                break;
            case TASK_COMPLETE:
                executorService.execute(new TaskCompleteRequest(this, intent));
                break;
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Tools.shutdownAndAwaitTermination(executorService);
        super.onDestroy();
    }

    public boolean isLogged() {
        return IS_LOGGED;
    }
}
