package ru.ID20.app.net.requests;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.db.RequestParser;
import ru.ID20.app.db.models.UserModel;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public class UserDataRequest extends BaseRequest {

    public static UserDataRequest getNewInstance(Context context, Intent intent){
        UserDataRequest userDataRequest = new UserDataRequest(context, intent);
        return userDataRequest;
    }

    public UserDataRequest(Context context, Intent intent) {
        super(context, intent);
        setIsLogged(false);
    }

    @Override
    String getLogTag() {
        return "UserDataRequest";
    }

    @Override
    public void run() {
        UserModel userModel = UserModel.getUserWithDefaultId();

        addNewValuePair(UserModel.SESSION_TOKEN, userModel.getSessionToken());

        setData();
        try {
            setResponseData(RequestParser.parseSimpleRequest(getResponseString()));
        } catch (Exception e) {
            userModel.setRequestStatus(RequestStatuses.ERROR.getStatus());
            userModel.setRequestError(e.getLocalizedMessage());
            userModel.save();
            return;
        }

        if (getResponseData().isSuccess()) {
            JSONObject jsonObject = getResponseData().getResponseJson();
            userModel.setUserId(jsonObject.optInt(UserModel.USER_ID));
            userModel.setUserSurename(jsonObject.optString(UserModel.USER_SURENAME));
            userModel.setUserName(jsonObject.optString(UserModel.USER_NAME));
            userModel.setUserPatronimic(jsonObject.optString(UserModel.USER_PATRONIMIC));
            userModel.setUserPhone(jsonObject.optString(UserModel.USER_PHONE));
            userModel.setRequestStatus(RequestStatuses.LOGGED.getStatus());
        } else {
            userModel.setRequestStatus(RequestStatuses.ERROR.getStatus());
            userModel.setRequestError(getResponseData().getErrorString());
        }
        userModel.save();
    }
}
