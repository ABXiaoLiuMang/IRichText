package com.zzhoujay.richtext.callback;

/**
 * Created by zhou on 2016/12/3.
 * 图片加载器
 */
public interface ImageGetter extends DrawableGetter {


    void registerImageLoadNotify(ImageLoadNotify imageLoadNotify);
}