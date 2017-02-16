package com.wzn.libaray.utils.image;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.wzn.libaray.utils.Logger;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by luona on 16/4/8.
 */
public class ImageUtils {
    public static String TAG = ImageUtils.class.getSimpleName();


    /**
     * 以平铺的方式排列图片
     *
     * @param width
     * @param height
     * @param src
     * @return
     */
    public static Bitmap createRepeaterBitmap(int width, int height, Bitmap src) {
        int coloumnNum = 1;
        int widthNum = 1;
        if (width > 0) {
            coloumnNum = width / src.getWidth() + 1;
        }
        if (height > 0) {
            width = height / src.getHeight() + 1;
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        for (int widithCount = 0; widithCount < coloumnNum; ++widithCount) {
            for (int heightCount = 0; heightCount < widthNum; heightCount++) {
                canvas.drawBitmap(src, widithCount * src.getWidth(), heightCount * src.getHeight(), null);
            }
        }

        return bitmap;
    }

    public static Bitmap getRoundBitmap(Bitmap scaleBitmapImage) {
        int targetWidth = 1000;
        int targetHeight = 1000;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
                sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
                targetHeight), null);
        return targetBitmap;
    }

    public static Drawable getRoundDrawable(Drawable d) {
        Bitmap b = getRoundBitmap(((BitmapDrawable) d).getBitmap());
        return new BitmapDrawable(b);
    }

    public static void showImage(Context context, String url, ImageView intoView) {
        Glide.with(context).
                load(url).into(new GlideDrawableImageViewTarget(intoView));
    }

    public static void showImage(Context context, int resId, ImageView intoView) {
        Glide.with(context).
                load(resId).into(new GlideDrawableImageViewTarget(intoView));
    }

    public static void showImage(Activity activity, String url, ImageView intoView) {
        Glide.with(activity).
                load(url).into(new GlideDrawableImageViewTarget(intoView));
    }

    public static void showImage(Activity activity, int resId, ImageView intoView) {
        Glide.with(activity).
                load(resId).into(new GlideDrawableImageViewTarget(intoView));
    }

    public static void displayCircleImage(Fragment fragment, String uri, int defaltResId, ImageView imageView){
        Glide.with(fragment).load(uri).placeholder(defaltResId).bitmapTransform(new CropCircleTransformation(fragment.getActivity())).into(new GlideDrawableImageViewTarget(imageView));
    }

    public static void displayCircleImage(Activity activity, String uri, int defaltResId, ImageView imageView){
        Glide.with(activity).load(uri).placeholder(defaltResId).bitmapTransform(new CropCircleTransformation(activity)).into(new GlideDrawableImageViewTarget(imageView));
    }


    public static void showImage(Fragment fragment, String url, ImageView intoView) {
        Glide.with(fragment).
                load(url).into(new GlideDrawableImageViewTarget(intoView));
    }

    public static void showImage(Fragment fragment, int resId, ImageView intoView) {
        Glide.with(fragment).
                load(resId).into(new GlideDrawableImageViewTarget(intoView));
    }

    public static Uri getContentUriFromFileUri(Context context, Uri uri) {
        String path = uri.getEncodedPath();
        if (path != null) {
            path = Uri.decode(path);
            ContentResolver cr = context.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.Images.ImageColumns._ID},
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
            } else {
                Uri uri_temp = Uri
                        .parse("content://media/external/images/media/"
                                + index);
                Logger.d(TAG, "uri_temp is " + uri_temp);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }

    public static void stopAnimWhenGif(ImageView img) {
        Drawable gifDrawable = img.getDrawable();
        if (null != gifDrawable && gifDrawable instanceof GifDrawable) {
            ((GifDrawable) gifDrawable).stop();
        }
    }

    public static void startAnimWhenGif(ImageView img) {
        Drawable gifDrawable = img.getDrawable();
        if (null != gifDrawable && gifDrawable instanceof GifDrawable) {
            ((GifDrawable) gifDrawable).start();
        }
    }
}
