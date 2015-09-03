package ru.ID20.app.db;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.ID20.app.db.models.TaskModel;
import ru.ID20.app.db.models.UserModel;
import ru.ID20.app.tools.Logger;
import ru.ID20.app.tools.Tools;

public class DbUtils {

    public static final String TAG = "DbUtils";
    public static final boolean IS_LOGGED = true;

    protected static final Logger LOG = Logger.getLogger(TAG, IS_LOGGED);

    public static final String DB_DATE_FORMAT = "dd.MM.yyy HH:mm:ss";
    public static final String DATE_AND_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final String CLAIM_LIST_DATE_FORMAT = "dd.MM.yy";
    public static final String CLAIM_TIME_FORMAT = "HH:mm";
    public static final String CLAIM_DATE_FORMAT = "dd/MM/yyyy";

    public static SimpleDateFormat dateFormat;

    public static <T extends Model> void bulkSave(List<T> list) {
        LOG.info("bulkSave - " + list.size());
        ActiveAndroid.beginTransaction();
        try {
            for (T t : list) {
                LOG.info("save");
                t.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static <T extends Model> void bulkDelete(List<T> listToDelete) {
        LOG.info("bulkDelete - " + listToDelete.size());
        ActiveAndroid.beginTransaction();
        try {

            for (T t : listToDelete) {
                LOG.info("delete");
                t.delete();
            }

            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static <T extends Model> void bulkSaveAndDelete(List<T> listToSave, List<T> listToDelete) {
        LOG.info("bulkSaveAndDelete");
        ActiveAndroid.beginTransaction();
        try {

            for (T t : listToDelete) {
                LOG.info("delete");
                t.delete();
            }

            for (T t : listToSave) {
                LOG.info("save" + t.save());
                t.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static String getDateStringForClaimListItem(String string) {
        return parseString(string, CLAIM_LIST_DATE_FORMAT);
    }

    public static String getDateString(String string) {
        return parseString(string, CLAIM_DATE_FORMAT);
    }

    private static String parseString(String string, String outputFormat) {
        dateFormat = new SimpleDateFormat(DB_DATE_FORMAT);
        Date date = null;

        try {
            date = dateFormat.parse(string);
            dateFormat.applyPattern(outputFormat);

        } catch (ParseException e) {
            LOG.debug("Wrong date format!");
            date = new Date();
        }

        return dateFormat.format(date);
    }

    public static String getDateString(Calendar calendar) {
        dateFormat = new SimpleDateFormat(CLAIM_DATE_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getTimeString(Calendar calendar) {
        dateFormat = new SimpleDateFormat(CLAIM_TIME_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getTimeString(String string) {
        return parseString(string, CLAIM_TIME_FORMAT);
    }

    public static String getDateAndTimeString(Calendar calendar) {
        dateFormat = new SimpleDateFormat(DB_DATE_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getDate(Calendar calendar) {
        dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getDateAndTime(Calendar calendar) {
        dateFormat = new SimpleDateFormat(DATE_AND_TIME_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

    public static String getDateAndTime(String string) {
        return parseString(string, DATE_AND_TIME_FORMAT);
    }


    //todo add here tables which should be cleared
    public static void clearDB() {
        Tools.ensureNotOnMainThread();
        UserModel.deleteAll();
        TaskModel.deleteAll();
    }

}
