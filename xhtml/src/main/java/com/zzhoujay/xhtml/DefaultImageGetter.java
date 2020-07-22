package com.zzhoujay.xhtml;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zzhoujay.xhtml.callback.ImageGetter;
import com.zzhoujay.xhtml.callback.ImageGetterCallBack;


public class DefaultImageGetter implements ImageGetter {

    private static final String TAG = DefaultImageGetter.class.getSimpleName();
    private Context context;
    private int maxWidth;//最大宽度 图片不要大于这个值
    private String baseUrl;


    public DefaultImageGetter(String baseUrl, int maxWidth, Context context) {
        this.context = context;
        this.maxWidth = maxWidth;
        this.baseUrl = baseUrl == null ? "" : baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }


    @Override
    public void getDrawable(final String source, final int start, final int end, final ImageGetterCallBack callBack) {
        if (callBack == null) return;
        String imageUrl = "https://cdn.pixabay.com/photo/2020/07/15/11/10/sky-5407339__340.jpg";
//        String imageUrl = source.startsWith("http") ? source : baseUrl + source;
        Glide.with(context).asBitmap().load(imageUrl).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                resource = scaleBitmap(resource, maxWidth);
                callBack.onImageReady(source, start, end, bmpToDrawable(source, resource));
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                callBack.onImageReady(source, start, end, placeholder);
            }
        });
    }

    private Drawable bmpToDrawable(String source, Bitmap b) {
        if (b == null) {
            return getPlaceHolder(source);
        } else {
            Drawable d = new BitmapDrawable(context.getResources(), b);
            d.setBounds(0, 0, b.getWidth(), b.getHeight());
            return d;
        }
    }

    private Drawable getPlaceHolder(String souce) {
        ColorDrawable colorDrawable = new ColorDrawable(0xffcccccc);
        if (souce == null || souce.isEmpty()) {
            colorDrawable.setBounds(0, 0, 120, 120);
        } else {
            colorDrawable.setBounds(0, 0, maxWidth, maxWidth / 2);
        }

        return colorDrawable;
    }


    private Bitmap scaleBitmap(Bitmap src, int dstWidth) {
        if (src == null) return null;
        int srcWidth = src.getWidth();
        if (srcWidth <= dstWidth) return src;

        float scale = dstWidth * 1.0f / srcWidth;
        int dstHeight = (int) (scale * src.getHeight());

        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) { // 如果没有缩放，那么不回收
            src.recycle(); // 释放Bitmap的native像素数组
        }
        return dst;
    }
}
