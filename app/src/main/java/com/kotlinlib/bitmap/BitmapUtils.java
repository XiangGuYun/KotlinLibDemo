package com.kotlinlib.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.scwang.smartrefresh.header.material.CircleImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by asus on 2017/12/12.
 */

public class BitmapUtils {


    /**
     * Bitmap转二进制字符串
     * @param bmp
     * @return
     */
    public static String bmpToString(Bitmap bmp){
        byte[] bytes = bmpToBytes(bmp);
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < bytes.length; i++) {
            if(i!=bytes.length-1){
                builder.append(bytes[i]+",");
            }else {
                builder.append(bytes[i]+"]");
            }
        }
        return builder.toString();
    }

    /**
     * 二进制字符串转Bitmap
     * str 二进制字符串
     * @return
     */
    public static Bitmap strToBitmap(String str){
        String[] strs = str.replace("[","").replace("]","").split(",");
        byte[] newBytes = new byte[strs.length];
        for (int i = 0; i < strs.length; i++) {
            newBytes[i] = Byte.parseByte(strs[i]);
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(newBytes, 0, newBytes.length);
        return bmp;
    }


    public static byte[] bmpToBytes(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();
        return datas;
    }

    /**
     * 压缩文件路径下的图片
     * @param filePath 文件路径
     * @param targetW 想要的宽度值
     * @param targetH 想要的高度值
     * @return
     */
    public static Bitmap decodeBitmap(String filePath, int targetW, int targetH){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置只解码图片的边框（宽高）数据，只为测出采样率
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片像素格式的首选配置
        BitmapFactory.decodeFile(filePath, options);//预加载
        //获取图片的原始宽高
        int originalW = options.outWidth;
        int originalH = options.outHeight;
        //设置采样大小
        options.inSampleSize = getSimpleSize(originalW, originalH, targetW, targetH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeBitmap(String filePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片像素格式的首选配置
        options.inSampleSize = 2;//设置采样率大小
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap decodeBitmap(Context ctx, int id, int targetW, int targetH){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//设置只解码图片的边框（宽高）数据，只为测出采样率
        options.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片像素格式的首选配置
        BitmapFactory.decodeResource(ctx.getResources(), id, options);//预加载
        //获取图片的原始宽高
        int originalW = options.outWidth;
        int originalH = options.outHeight;
        //设置采样大小
        options.inSampleSize = getSimpleSize(originalW, originalH, targetW, targetH);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(ctx.getResources(), id, options);
    }

    /**
     * 压缩Bitmap质量
     * @param bitmap
     * @param quality 1-100
     * @return
     */
    public static Bitmap compressQuality(Bitmap bitmap, int quality){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static void saveBitmapToSDCard(Bitmap bitmap, String fileName, int quality) {
        File f = new File(Environment.getExternalStorageDirectory().toString(), fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static Bitmap compressSize(Bitmap bitmap, int targetW, int targetH){
        return Bitmap.createScaledBitmap(bitmap, targetW, targetH, true);
    }

    /**
     * 计算采样率
     */
    private static int getSimpleSize(int originalW, int originalH, int targetW, int targetH) {
        int sampleSize = 1;
        if(originalW > originalH && originalW > targetW){//以宽度来计算采样值
            sampleSize = originalW/targetW;
        }else if(originalW < originalH && originalH > targetH){
            sampleSize = originalH/targetH;
        }
        if(sampleSize <= 0){
            sampleSize = 1;
        }
        return sampleSize;
    }

    /**
     * 显示网络图片
     */
    public static void showBitmap(Context ctx, ImageView iv, String imgUrl,int placeImg,int errorImg) {
        Glide.with(ctx).load(imgUrl).crossFade().placeholder(placeImg)
                .error(errorImg)
                //.override(320, 240).
                .priority(Priority.IMMEDIATE)
                .fitCenter()
                .into(new GlideDrawableImageViewTarget(iv) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //在这里添加一些图片加载完成的操作
                    }
                });
    }

    /**
     * 用该方法显示圆形图片，否则第一次将显示默认图
     * @param ctx
     * @param iv
     * @param imgUrl
     */
    public static void showCircleBitmap(Context ctx, final CircleImageView iv, String imgUrl, int placeImg, int errorImg) {
        Glide.with(ctx)
                .load(imgUrl)
                .asBitmap()
                .placeholder(placeImg)
                .error(errorImg)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        iv.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                    }
                });

    }


    public static void showBitmap(Context ctx, ImageView iv, String imgUrl, final onFinishedLoad finishedLoad,
                                  final onFailedLoad failedLoad, int placeImg, int errorImg) {
        Glide.with(ctx).load(imgUrl).crossFade().placeholder(placeImg).error(errorImg).thumbnail(1.0f)
                .override(320, 240).
                priority(Priority.IMMEDIATE)
                .into(new GlideDrawableImageViewTarget(iv) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        //在这里添加一些图片加载完成的操作
                        finishedLoad.finish(drawable, anim);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        failedLoad.fail(e, errorDrawable);
                    }
                });

    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }


    public interface onFinishedLoad{
        void finish(GlideDrawable drawable, GlideAnimation anim);
    }

    public interface onFailedLoad{
        void fail(Exception e, Drawable errorDrawable);
    }
}
