package com.example.common.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.File;

/**
 * @author lpp
 */
public interface ILoader {
    void init(Context context);

    void loadUrl(ImageView target, String url, Options options);

    void loadCircle(ImageView target, Object url, Options options);

    void loadResource(ImageView target, int resId, Options options);

    void loadDrawable(ImageView target, Drawable drawable, Options options);

    void loadResGif(ImageView target, int resId, Options options);

    void loadAssets(ImageView target, String assetName, Options options);

    void loadFile(ImageView target, File file, Options options);

    void clearMemoryCache(Context context);

    void clearDiskCache(Context context);

    void loadCornerCircle(ImageView target, String url, Options options, CornerTransform cornerTransform);

    void loadCornerCircleOptimize(ImageView target, String url, Options options, CornerOptimizeTransform cornerTransform);

    class Options {

        public static final int RES_NONE = -1;
        //加载中
        public int loadingResId = RES_NONE;
        //加载失败
        public int loadErrorResId = RES_NONE;
        //加载空



        public Options(int loadingResId, int loadErrorResId) {
            this.loadingResId = loadingResId;
            this.loadErrorResId = loadErrorResId;
        }
    }
}
