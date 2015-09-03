package ru.ID20.app.net.requests;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.db.RequestParser;
import ru.ID20.app.db.models.UserModel;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public class LoginRequest extends BaseRequest {

    public static LoginRequest getNewInstance(Context context, Intent intent) {
        return new LoginRequest(context, intent);
    }

    private String login, pass;

    public LoginRequest(Context context, Intent intent) {
        super(context, intent);
        this.login = getArg().getString(UserModel.USER_LOGIN);
        this.pass = getArg().getString(UserModel.USER_PASS);
        setIsLogged(false);
    }

    @Override
    public void run() {
        UserModel userModel = UserModel.getUserWithDefaultId();
        userModel.setRequestStatus(RequestStatuses.SENT.getStatus());
        userModel.setUserId(Constants.DEFAULT_USER_ID);
        userModel.setUserLogin(login);
        userModel.setUserPass(pass);
        userModel.save();

        addNewValuePair("login", login);
        addNewValuePair("password", pass);

        setData();
        try {
            setResponseData(RequestParser.parseSimpleRequest(getResponseString()));
        } catch (Exception e) {
            e.printStackTrace();
            userModel.setRequestStatus(RequestStatuses.ERROR.getStatus());
            userModel.setRequestError(e.getLocalizedMessage());
            userModel.save();
            return;
        }

        if (getResponseData().isSuccess()) {
            JSONObject jsonObject = getResponseData().getResponseJson();
            userModel.setSessionToken(jsonObject.optString(UserModel.SESSION_TOKEN));
            userModel.setUserRole(jsonObject.optString(UserModel.USER_ROLE));
            userModel.setRequestStatus(RequestStatuses.SUCCESS.getStatus());
        } else {
            userModel.setRequestStatus(RequestStatuses.ERROR.getStatus());
            userModel.setRequestError(getResponseData().getErrorString());
        }
        userModel.save();
    }

    @Override
    String getLogTag() {
        return "LoginRequest";
    }
}
