package ru.ID20.app;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

import ru.ID20.app.tools.Tools;

/**
 * Created  by  s.shevchenko  on  29.06.2015.
 */
public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    private static volatile LruCache<String, Bitmap> mMemoryCache;

    static {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 6;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Tools.enableStrictMode();
        } else {

        }
        instance = this;
        ActiveAndroid.initialize(this);
    }

    public static LruCache<String, Bitmap> getMemoryCache() {
        return mMemoryCache;
    }
}
