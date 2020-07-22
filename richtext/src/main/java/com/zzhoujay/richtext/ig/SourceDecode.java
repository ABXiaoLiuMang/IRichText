package com.zzhoujay.richtext.ig;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;

import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.drawable.GifDrawable;
import com.zzhoujay.richtext.ext.ImageKit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zhou on 2017/2/21.
 */
abstract class SourceDecode<T> {

    static SourceDecode<InputStream> REMOTE_SOURCE_DECODE = new SourceDecode<InputStream>() {

        private static final int MARK_POSITION = 1024 * 1024;

        @Override
        void decodeSize(InputStream inputStream, BitmapFactory.Options options) {
            BufferedInputStream stream;
            if (inputStream instanceof BufferedInputStream) {
                stream = (BufferedInputStream) inputStream;
            } else {
                stream = new BufferedInputStream(inputStream);
            }
            if (options.inJustDecodeBounds) {
                stream.mark(MARK_POSITION);
            }
            BitmapFactory.decodeStream(stream, null, options);
            if (options.inJustDecodeBounds) {
                try {
                    stream.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public ImageWrapper decodeAsBitmap(InputStream inputStream, BitmapFactory.Options options) {
            BufferedInputStream stream;
            if (inputStream instanceof BufferedInputStream) {
                stream = (BufferedInputStream) inputStream;
            } else {
                stream = new BufferedInputStream(inputStream);
            }
            if (options.inJustDecodeBounds) {
                stream.mark(MARK_POSITION);
            }
            Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
            if (options.inJustDecodeBounds) {
                try {
                    stream.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return ImageWrapper.createAsBitmap(bitmap);
        }

        @Override
        ImageWrapper decodeAsGif(InputStream inputStream, BitmapFactory.Options options) {
            return ImageWrapper.createAsGif(new GifDrawable(Movie.decodeStream(inputStream), options.outHeight, options.outWidth));
        }

        @Override
        boolean isGif(InputStream inputStream, BitmapFactory.Options options) {
            return ImageKit.isGif(inputStream);
        }
    };

    ImageWrapper decode(ImageHolder holder, T t, BitmapFactory.Options options) {
        if (holder.isAutoPlay() && isGif(t, options)) {
            holder.setImageType(ImageHolder.ImageType.GIF);
            return decodeAsGif(t, options);
        } else {
            return decodeAsBitmap(t, options);
        }
    }

    abstract boolean isGif(T t, BitmapFactory.Options options);

    abstract void decodeSize(T t, BitmapFactory.Options options);

    abstract ImageWrapper decodeAsBitmap(T t, BitmapFactory.Options options);

    abstract ImageWrapper decodeAsGif(T t, BitmapFactory.Options options);

}
