package com.example.common.glide;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;


/**
 * @author lpp
 */
public class GlideLoader implements ILoader {
    @Override
    public void init(Context context) {
        try {
            Class.forName("com.bumptech.glide.Glide");
            Glide.get(context).setMemoryCategory(MemoryCategory.HIGH);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Must be dependencies Glide!");
        }
    }

    @Override
    public void loadUrl(ImageView target, String url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options,false,null);
    }

    @Override
    public void loadCircle(ImageView target, Object url, Options options) {
        load(getRequestManager(target.getContext()).load(url), target, options,true,null);
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options,false,null);
    }

    @Override
    public void loadDrawable(ImageView target, Drawable drawable, Options options) {
        load(getRequestManager(target.getContext()).load(drawable), target, options,false,null);
    }

    @Override
    public void loadResGif(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).asGif().load(resId),target,options,false,null);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options,false,null);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options,false,null);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void loadCornerCircle(ImageView target, String url, Options options, CornerTransform cornerTransform) {
        load(getRequestManager(target.getContext()).load(url), target, options,false,cornerTransform);
    }

    @Override
    public void loadCornerCircleOptimize(ImageView target, String url, Options options, CornerOptimizeTransform cornerTransform) {
        load(getRequestManager(target.getContext()).asBitmap().load(url), target, options,false,cornerTransform);
    }

    private RequestManager getRequestManager(Context context) {
        return Glide.with(context);
    }

    @SuppressLint("CheckResult")
    private void load(RequestBuilder request, ImageView target, Options options, boolean isCircle, Transformation<Bitmap> cornerTransform) {
        if (options==null){
            return;
        }
        RequestOptions requestOptions=new RequestOptions()
                .placeholder(options.loadingResId)
                .error(options.loadErrorResId);

        if (options.loadingResId != Options.RES_NONE) {
            requestOptions.placeholder(options.loadErrorResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            requestOptions.error(options.loadErrorResId).fallback(options.loadErrorResId);
        }
        if (isCircle){
            requestOptions.circleCrop();
//            requestOptions.centerCrop();
        }
        if (cornerTransform != null){
            if (cornerTransform instanceof CornerTransform){
                requestOptions.transform(cornerTransform);
            } else if (cornerTransform instanceof CornerOptimizeTransform){
                requestOptions.transform(cornerTransform);
            }
        }

        request.apply(requestOptions).into(target);
    }
}
