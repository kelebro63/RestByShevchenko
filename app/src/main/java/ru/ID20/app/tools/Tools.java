package ru.ID20.app.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.os.StrictMode;
import android.util.TypedValue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import ru.ID20.app.R;
import ru.ID20.app.ui.activities.LoginActivity;
import ru.ID20.app.ui.activities.MainActivity;

/**
 * Created  by  s.shevchenko  on  29.06.2015.
 */
public class Tools {

    public static void enableStrictMode() {
        StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
        StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();
        threadPolicyBuilder.penaltyFlashScreen();
        vmPolicyBuilder.setClassInstanceLimit(MainActivity.class, 1)
                .setClassInstanceLimit(LoginActivity.class, 1);
        StrictMode.setThreadPolicy(threadPolicyBuilder.build());
        StrictMode.setVmPolicy(vmPolicyBuilder.build());
    }

    public static void shutdownAndAwaitTermination(ExecutorService... pools) {

        for (ExecutorService pool : pools) {

            pool.shutdown();
            try {
                if (!pool.awaitTermination(10, TimeUnit.MILLISECONDS))
                    pool.shutdownNow();

                if (!pool.awaitTermination(10, TimeUnit.MILLISECONDS))
                    System.err.println("Pool did not terminate");
            } catch (InterruptedException ie) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    public static Bitmap createBitmapFromResources(int drawableId, int actionbarSize, Context context){
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), drawableId), actionbarSize, actionbarSize, false);
    }

    public static void ensureNotOnMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper())
            return;
        throw new IllegalStateException("This method cannot be called from the UI thread.");
    }

}
