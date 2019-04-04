package com.example.common.utils;


import com.example.common.glide.GlideLoader;
import com.example.common.glide.ILoader;

/**
 * @author lpp
 */
public class LoaderImage {

    private static ILoader innerLoader;

    public static ILoader getLoader() {
        if (innerLoader == null) {
            synchronized (LoaderImage.class) {
                if (innerLoader == null) {
                    innerLoader = new GlideLoader();
                }
            }
        }
        return innerLoader;
    }
}
