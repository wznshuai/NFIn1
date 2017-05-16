package com.wzn.libaray.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class CommonUtils {
    public static final String pattern = "#,###,##0.00";

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Resources res, float dpValue) {
        return new BigDecimal(
                Double.toString(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                        res.getDisplayMetrics()))
        ).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        /*
         * final float scale =
		 * context.getResources().getDisplayMetrics().density; return (int)
		 * (dpValue * scale + 0.5f);
		 */
        return dp2px(context.getResources(), dpValue);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 解压缩一个文件
     *
     * @param zipFile 压缩文件
     * @param desDir  解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static void upZipFile(File zipFile, File desDir) throws IOException {
        String tag = "unzip file";
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            InputStream in = zf.getInputStream(entry);

            File desFile = new File(desDir, File.separator + entry.getName());
            if (entry.isDirectory()) {
                desFile.mkdirs();
            } else {
                Logger.d(tag, "unzip file : " + desFile.getPath());
                if (!desFile.exists()) {
                    Logger.d(tag, desFile.getPath() + "  not exists");
                    File fileParentDir = desFile.getParentFile();
                    if (!fileParentDir.exists()) {
                        Logger.d(tag, "parent file not exists : " + fileParentDir.getPath());
                        Logger.d(tag, "create parent dirs result is " + fileParentDir.mkdirs());
                    } else {
                        if (!fileParentDir.isDirectory()) {
                            Logger.d(tag, "parent file " + fileParentDir.getPath() + "  already exists, but not a directory");
                            fileParentDir.deleteOnExit();
                            fileParentDir.mkdirs();
                        } else {
                            Logger.d(tag, "parent file " + fileParentDir.getPath() + "  already exists");
                        }
                    }
                    desFile.createNewFile();
                } else {
                    Logger.d(tag, desFile.getPath() + " already exists");
                }
                OutputStream out = new FileOutputStream(desFile);
                byte buffer[] = new byte[4096];
                int realLength;
                while ((realLength = in.read(buffer)) != -1) {
                    out.write(buffer, 0, realLength);
                }
                out.flush();
                out.close();
                in.close();
            }
        }
    }

    public static String formatNum(Object d) {
        return formatNum(d, pattern);
    }

    public static String formatNum(double d) {
        return formatNum(d, pattern);
    }

    public static String formatNum(double d, RoundingMode r) {
        return formatNum(d, pattern, r);
    }

    public static String formatNum(Object d, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        return decimalFormat.format(d);
    }

    public static String formatNum(float d, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        return decimalFormat.format(d);
    }

    public static String formatNum(double d, String pattern) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        return decimalFormat.format(d);
    }

    public static String formatNum(double d, String pattern, RoundingMode r) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(r);
        return decimalFormat.format(d);
    }

    public static String formatNum(float f) {
        return formatNum(f, pattern);
    }

    public static String formatNum(int d) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        return decimalFormat.format(d);
    }

    public static String formatNum(long l) {
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.setRoundingMode(RoundingMode.HALF_EVEN);
        return decimalFormat.format(l);
    }

    public static void copyToClipBoard(Context context, String lable, @NonNull String value) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(lable, value));
    }

}
