package com.zzhoujay.richtext;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zzhoujay.richtext.callback.ImageGetter;
import com.zzhoujay.richtext.callback.ImageLoadNotify;

import java.util.concurrent.ExecutionException;

/**
 * Created by zhou on 2016/12/8.
 * RichText默认使用的图片加载器
 * 支持本地图片，Gif图片，图片缓存，图片缩放等等功能
 */
public class DefaultImageGetter implements ImageGetter, ImageLoadNotify {


    private int loadedCount = 0;
    private ImageLoadNotify notify;



    @Override
    public Drawable getDrawable(final ImageHolder holder, final RichTextConfig config, TextView textView) {
        try {
            return Glide.with(textView.getContext())
                    .load("http://wx1.sinaimg.cn/mw690/eaaf2affly1fihvjpekzwj21el0qotfq.jpg")
                    .submit()
                    .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getDrawableGlide(String url, CustomTarget<Drawable> customTarget) {

    }


    @Override
    public void registerImageLoadNotify(ImageLoadNotify imageLoadNotify) {
        this.notify = imageLoadNotify;
    }

    @Override
    public void done(Object from) {
        loadedCount++;
        if (notify != null) {
            notify.done(loadedCount);
        }
    }

}

