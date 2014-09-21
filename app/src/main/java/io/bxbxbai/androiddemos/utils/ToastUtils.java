package io.bxbxbai.androiddemos.utils;

import android.content.Context;
import android.widget.Toast;

import io.bxbxbai.androiddemos.AppController;

/**
 * Created by baia on 14-9-21.
 */
public class ToastUtils {
    private ToastUtils() {
    }

    private static void show(Context context, int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }

    private static void show(Context context, String message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    public static void showShort(int resId) {
        Toast.makeText(AppController.getInstance(), resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(AppController.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(int resId) {
        Toast.makeText(AppController.getInstance(), resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(AppController.getInstance(), message, Toast.LENGTH_LONG).show();
    }
}
