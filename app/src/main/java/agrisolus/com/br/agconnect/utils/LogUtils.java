package agrisolus.com.br.agconnect.utils;

import android.util.Log;

import agrisolus.com.br.agconnect.BuildConfig;

/**
 * Created by gilbe on 06/02/2017.
 */

public class LogUtils {

    private static boolean DEBUG = true;
    
    public static void d(final String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void v(final String tag, String message) {
        if (DEBUG) {
            Log.v(tag, message);
        }
    }

    public static void i(final String tag, String message) {
        if (DEBUG) {
            Log.i(tag, message);
        }
    }

    public static void w(final String tag, String message) {
        if (DEBUG) {
            Log.w(tag, message);
        }
    }

    public static void e(final String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void e(final String tag, String message, Throwable t) {
        if (DEBUG) {
            Log.e(tag, message, t);
        }
    }

}
