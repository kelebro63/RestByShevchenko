package ru.ID20.app.tools;

import org.json.JSONException;
import org.json.JSONObject;

import ru.ID20.app.db.models.UserModel;

/**
 * Created by Sergey on 27.12.2014.
 */
public class NetUtils {

    /**
     * Метод возвращает JSON объект с ключем "access_token" со занчением sessionToken.
     */
    public synchronized static JSONObject getBaseJSON(){
        Tools.ensureNotOnMainThread();
        JSONObject object = new JSONObject();
        try {
            object.put("access_token", UserModel.getLoggedUser().getSessionToken());
            object.put("archive",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public synchronized static JSONObject getBaseJSONForArchives(){
        Tools.ensureNotOnMainThread();
        JSONObject object = new JSONObject();
        try {
            object.put("access_token", UserModel.getLoggedUser().getSessionToken());
            object.put("archive",1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
