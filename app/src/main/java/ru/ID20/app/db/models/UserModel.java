package ru.ID20.app.db.models;

import android.provider.BaseColumns;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.RequestStatuses;
import ru.ID20.app.tools.Tools;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
@Table(name = "User", id = BaseColumns._ID)
public class UserModel extends BaseModel {

    public static final String USER_ID = "id";
    @Column(name = USER_ID, unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    @SerializedName("id")
    private Integer userId;

    public static final String SESSION_TOKEN = "access_token";
    @SerializedName(SESSION_TOKEN)
    @Column(name = SESSION_TOKEN)
    private String sessionToken;

    public static final String USER_LOGIN = "login";
    @Column(name = USER_LOGIN)
    @SerializedName(USER_LOGIN)
    private String userLogin;

    public static final String USER_PASS = "password";
    @Column(name = USER_PASS)
    @SerializedName(USER_PASS)
    private String userPass;

    public static final String USER_NAME = "name";
    @Column(name = USER_NAME)
    @SerializedName(USER_NAME)
    private String userName;

    public static final String USER_SURENAME = "surname";
    @Column(name = USER_SURENAME)
    @SerializedName(USER_SURENAME)
    private String userSurename;

    public static final String USER_PATRONIMIC = "patronymic";
    @Column(name = USER_PATRONIMIC)
    @SerializedName(USER_PATRONIMIC)
    private String userPatronimic;

    public static final String USER_PHONE = "phone";
    @Column(name = USER_PHONE)
    @SerializedName(USER_PHONE)
    private String userPhone;

    public static final String USER_ROLE = "role";
    @Column(name = USER_ROLE)
    @SerializedName(USER_ROLE)
    private String userRole;

    public static void deleteAll() {
        Tools.ensureNotOnMainThread();
        new Delete().from(UserModel.class).execute();
    }

    public static String getToken(int userId) {
        UserModel model = new Select().from(UserModel.class).where(USER_ID + " = ?", String.valueOf(userId)).executeSingle();
        if (model != null) {
            return model.getSessionToken();
        }
        return null;
    }

    public static UserModel getUserWithDefaultId() {
        UserModel model = new Select().from(UserModel.class).where(USER_ID + " = ?", String.valueOf(Constants.DEFAULT_USER_ID)).executeSingle();
        if (model == null) {
            model = new UserModel();
        }
        return model;
    }

    public static UserModel getLoggedUser() {
        return new Select().from(UserModel.class).where(REQUEST_STATUS + " = ?", String.valueOf(RequestStatuses.LOGGED.getStatus())).executeSingle();

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurename() {
        return userSurename;
    }

    public void setUserSurename(String userSurename) {
        this.userSurename = userSurename;
    }

    public String getUserPatronimic() {
        return userPatronimic;
    }

    public void setUserPatronimic(String userPatronimic) {
        this.userPatronimic = userPatronimic;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
