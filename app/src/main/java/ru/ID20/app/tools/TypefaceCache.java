package ru.ID20.app.tools;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class TypefaceCache {

    protected final Logger LOG = Logger.getLogger("TypefaceCache", false);

    private static TypefaceCache instance = new TypefaceCache();

    public static TypefaceCache getInstance() {
        return instance;
    }

    private final HashMap<String, Typeface> map = new HashMap<String, Typeface>();

    public Typeface getTypeface(String file, Context context) {
        Typeface result = map.get(file);
        if (result == null) {
            LOG.info("Typeface result == null; file - " + file);
            result = Typeface.createFromAsset(context.getAssets(), file);
            map.put(file, result);
        }
        return result;
    }
}