package ru.ID20.app.db;

import org.json.JSONArray;
import org.json.JSONObject;

import ru.ID20.app.db.models.CollectionsModel;

/**
 * Created by Sergey on 09.11.2014.
 */
public class ResponseData {

    private boolean isSuccess = false;
    private JSONObject responseJson;
    private JSONArray responseArray;
    private String errorString;
    private String updateTime;
    private CollectionsModel collectionsModel;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public JSONObject getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(JSONObject responseJson) {
        this.responseJson = responseJson;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public JSONArray getResponseArray() {
        return responseArray;
    }

    public void setResponseArray(JSONArray responseArray) {
        this.responseArray = responseArray;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public CollectionsModel getCollectionsModel() {
        return collectionsModel;
    }

    public void setCollectionsModel(CollectionsModel collectionsModel) {
        this.collectionsModel = collectionsModel;
    }
}
