package cn.lspush.spruce.utility;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 *
 */
public class AndroidLog {
    private static final int MAX_LOG_LENGTH = 3500; //[3690,3700)
    public static final AndroidLog DEFAULT = new AndroidLog();

    public void log(int priority, String finalTag, String tag, @Nullable String message, @Nullable Throwable cause) {
        if (message != null) {
            log(priority, finalTag, message);
        }
        if (cause != null) {
            Log.println(priority, finalTag, Log.getStackTraceString(cause));
        }
    }

    private void log(int priority, String tag, @NonNull String message) {
        if (message.length() < MAX_LOG_LENGTH) {
            Log.println(priority, tag, message);
            return;
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                String part = message.substring(i, end);
                Log.println(priority, tag, part);
                i = end;
            } while (i < newline);
        }
    }
}
