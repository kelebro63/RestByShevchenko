package ru.ID20.app.tools;

import ru.ID20.app.BuildConfig;

public class Logger {

    private String className;
    private boolean isEnabled;

    private Logger(String name, boolean isEnabled) {
        int i = name.lastIndexOf('.');
        className = name.substring(i + 1);
        this.isEnabled = isEnabled;
    }

    public static Logger getLogger(String tag) {
        return getLogger(tag, false);
    }

    public static Logger getLogger(String tag, boolean isEnabled) {
        if (tag == null)
            throw new NullPointerException();
        return new Logger(tag, isEnabled);
    }

    public void debug(String msg) {
        if (BuildConfig.DEBUG && isEnabled)
            android.util.Log.d(className + ": " + getThread(), "[" + msg + "]");
    }

    public void warning(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(className + ": " + getThread(), "[" + msg + "]");
    }

    public void info(String msg) {
        if (BuildConfig.DEBUG && isEnabled)
            android.util.Log.i(className + ": " + getThread(), "[" + msg + "]");
    }

    public void error(String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(className + ": " + getThread(), "[" + msg + "]");
    }

    private String getThread() {
        return Thread.currentThread().getName();
    }
}
