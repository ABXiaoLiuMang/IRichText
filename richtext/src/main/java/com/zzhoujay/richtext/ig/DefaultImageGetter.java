package com.zzhoujay.richtext.ig;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.ImageGetter;
import com.zzhoujay.richtext.callback.ImageLoadNotify;

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
        return null;
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
