/**
 * Copyright (c) 2013 Cangol
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wzn.libaray.utils.device;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class DeviceInfo {
    public static final String SPECIAL_IMEI = "000000000000000";
    public static final String SPECIAL_ANDROID_ID = "9774d56d682e549c";
    public static final String MOKUN_GAMESDK = "MOKUN_GAMESDK";

    public static String getOS() {
        return "Android";
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getMobileInfo() {
        StringBuilder sb = new StringBuilder();
        try {

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name).append("=").append(value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getCPUInfo() {
        try {
            byte[] bs = new byte[1024];
            RandomAccessFile reader = new RandomAccessFile("/proc/cpuinfo", "r");
            reader.read(bs);
            String ret = new String(bs);
            int index = ret.indexOf(0);
            if (index != -1) {
                return ret.substring(0, index);
            } else {
                return ret;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getResolution(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return metrics.heightPixels + "x" + metrics.widthPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    static public Size getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display dp = wm.getDefaultDisplay();
        Point point = new Point();
        dp.getSize(point);
        return new Size(point.x, point.y);
    }


    public static String getIMEI(Context context) {
        String imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
        if (null == imei || imei.trim().equals("")) {
            imei = getDeviceId(context);
        }
        return imei;
    }

    public static String getDeviceId(Context pContext) {

        if (pContext == null)
            return "";

        String deviceToken = null;


        TelephonyManager telephonyManager = (TelephonyManager) pContext
                .getSystemService(Context.TELEPHONY_SERVICE);


        String tmDevice = "" + telephonyManager.getDeviceId();
        String tmSerial = "" + telephonyManager.getSimSerialNumber();
        String androidId = ""
                + android.provider.Settings.Secure.getString(
                pContext.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode()
                        | MOKUN_GAMESDK.hashCode());

        String uniqueId = deviceUuid.toString();

        deviceToken = uniqueId;// 00000000-7a48-5a0a-22a5-c1db1d04d453

        return deviceToken;
    }

    public static int getNetworkType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.getType();
        } else {
            return -1;
        }
    }


    public static String getDeviceSize(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        String str = "";

        int dpi = metrics.densityDpi;

        if(dpi >= 160 && dpi < 240) {
            str = "m";
        } else if (dpi >=240 && dpi < 320) {
            str = "h";
        } else if (dpi >= 320 && dpi < 480) {
            str = "xh";
        } else if (dpi >= 480 && dpi < 640) {
            str = "xxh";
        } else if (dpi >= 640) {
            str = "xxxh";
        }

        return str;
    }

    /**
     * return application is background
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND || appProcess.importance == RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE;
            }
        }
        return false;
    }

    public static String getAppVersion(Context context) {
        String result = "UNKNOWN";
        try {
            result = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return result;
    }

    public static int getAppVersionCode(Context context) {
        int result = 0;
        try {
            result = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return result;
    }

    static public class Size {
        public float width;
        public float height;

        public Size() {
            width = 0;
            height = 0;
        }

        public Size(int w, int h) {
            width = w;
            height = h;
        }

        @Override
        public String toString() {
            StringBuffer str = new StringBuffer();
            str.append(height).append("x").append(width);
            return str.toString();
        }
    }
}
