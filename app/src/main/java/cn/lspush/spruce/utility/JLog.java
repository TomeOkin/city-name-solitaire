package cn.lspush.spruce.utility;

import android.util.Log;

/**
 *
 */
public class JLog {
    private static final AndroidLog log = AndroidLog.DEFAULT;

    public static void i(String tag, String msg) {
        log.log(Log.INFO, tag, tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable cause) {
        log.log(Log.INFO, tag, tag, msg, cause);
    }

    public static void e(String tag, String msg) {
        log.log(Log.ERROR, tag, tag, msg, null);
    }

    public static void e(String tag, String msg, Throwable cause) {
        log.log(Log.ERROR, tag, tag, msg, cause);
    }
}
