package ru.ID20.app.net.requests;

import android.content.Context;
import android.content.Intent;

import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.db.DbUtils;
import ru.ID20.app.db.RequestParser;
import ru.ID20.app.db.models.CollectionsModel;
import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.db.models.UserModel;
import ru.ID20.app.net.RequestManager;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public class TaskListRequest extends BaseRequest {

    private boolean isUpdate;
    private int currentPage;
    private Context context;


    public TaskListRequest(Context context, Intent intent) {
        super(context, intent);
        this.context =context;
        this.currentPage = getArg().getInt(Constants.CURRENT_PAGE_ARG);
        this.isUpdate = getArg().getBoolean(Constants.IS_UPDATE_ARG);
        setIsLogged(false);
    }

    @Override
    String getLogTag() {
        return "TaskListRequest";
    }


    @Override
    public void run() {

        TaskModel task = new TaskModel();

        addNewValuePair("access_token", UserModel.getLoggedUser().getSessionToken());
        addNewValuePair("pageSize", String.valueOf(Constants.ROWS_COUNT_IN_REQUEST));

        if (isUpdate) {
            addNewValuePair("updatedOnly", String.valueOf(1));
        } else {
            task.setRequestStatus(RequestStatuses.SENT.getStatus());
            task.save();
        }
        if (currentPage > 0){
            addNewValuePair("currentPage", String.valueOf(currentPage));
        }

        setData();
        try {
            setResponseData(RequestParser.parseSimpleRequest(getResponseString()));
        } catch (Exception e) {
            task.setRequestStatus(RequestStatuses.ERROR.getStatus());
            task.setRequestError(e.getLocalizedMessage());
            task.save();
            task.delete();
            return;
        }

        if (getResponseData().isSuccess()) {
            CollectionsModel collectionsModel = getCollectionModel(getResponseData().getResponseJson());
            if (collectionsModel.getCurrentPage() == Constants.START_PAGE_NUMBER && collectionsModel.getTaskList().isEmpty() && !isUpdate){
                task.setRequestStatus(RequestStatuses.EMPTY.getStatus());
                task.save();
            }
            if (collectionsModel.getCurrentPage() == Constants.START_PAGE_NUMBER && collectionsModel.getPagesCount() > 1){
                for (int i = 1 ; i < collectionsModel.getPagesCount(); i++) {
                    RequestManager.startTaskListRequest(context, isUpdate, i);
                }
            }
            for (TaskModel taskModel : collectionsModel.getTaskList()){
                taskModel.setRequestStatus(RequestStatuses.SUCCESS.getStatus());
            }

            if (!collectionsModel.getTaskList().isEmpty()){
                task.delete();
            }

            DbUtils.bulkSave(collectionsModel.getTaskList());
        }
    }
}
